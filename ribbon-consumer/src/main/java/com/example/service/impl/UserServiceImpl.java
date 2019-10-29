package com.example.service.impl;

import com.example.entity.User;
import com.example.service.IUserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.List;
import java.util.concurrent.Future;

/**
 * UserServiceImpl
 * @author liushun
 * @since JDK 1.8
 **/
@Service
public class UserServiceImpl implements IUserService {
    // region 私有

    @Autowired
    private RestTemplate restTemplate;

    private static final String GROUP_KEY = "UserGroup";
    private static final String THREADPOOL_KEY = "UserByIdThread";

    // endregion

    // region 基本例子

    /**
     * 同步 Gets user by id.
     * ignoreExceptions = {Exception.class} 当方法抛出异常时，Hystrix会将他包装成 HystrixBadRequestException 抛出，这样就不会触发后续的fallback逻辑
     * 线程池划分(建议加`threadPoolKey`) 默认使用 groupKey 划分，如果存在 threadPoolKey 使用 threadPoolKey 进行划分
     * @CacheResult 注解设置请求缓存，key生成方法 `getUserByIdCacheKey`
     * 如果设置了 cacheKeyMethod @CacheKey 注解不会生效
     * @param id the id
     * @return the user by id
     */
    @Override
    // @HystrixCommand(fallbackMethod = "defaultUser", ignoreExceptions = {Exception.class})
    @CacheResult(cacheKeyMethod = "getUserByIdCacheKey")
    @HystrixCommand(fallbackMethod = "defaultUser", commandKey = "getUserById", groupKey = GROUP_KEY, threadPoolKey = THREADPOOL_KEY)
    public User getUserById(@CacheKey("id") Long id) {
        return restTemplate.getForObject("http://hello-server/users/{1}", User.class, id);
    }

    /**
     * Update.
     * @CacheRemove(commandKey = "getUserById") 清理缓存，指定commandKey
     * @param user the user
     */
    @Override
    @CacheRemove(commandKey = "getUserById")
    public void update(User user) {
        restTemplate.postForObject("http://hello-server/users", user, User.class);
        return;
    }

    /**
     * 异步 Gets user by id async.
     * @param id the id
     * @return the user by id async
     */
    @Override
    @HystrixCommand
    public Future<User> getUserByIdAsync(final String id) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://hello-server/users/{1}", User.class, id);
            }
        };
    }

    /**
     * 响应式 Gets user by id async.
     * 参数 observableExecutionMode = ObservableExecutionMode.EAGER 表示 observe()
     * observableExecutionMode = ObservableExecutionMode.LAZY 表示 toObserve()
     * @param id the id
     * @return the user by id async
     */
    @Override
    @HystrixCommand
    public Observable<User> getUserById(String id) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    if(!subscriber.isUnsubscribed()) {
                        User user = restTemplate.getForObject("http://hello-server/users/{1}", User.class, id);
                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    }
                } catch(Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 服务降级方式，在一个类中 所以与 访问修饰符 无关
     * Throwable e 直接加入可以获取触发服务降级的异常信息
     * 可以使用级联降级
     * @return
     */
    // @HystrixCommand(fallbackMethod = "defaultUserSec")
    private User defaultUser(Throwable e) {
        return new User("First");
    }

    private User defaultUserSec(Throwable e) {
        return new User("Second");
    }

    private Long getUserByIdCacheKey(Long id) {
        return id;
    }

    // endregion

    // region 合并请求例子 使用注解实现请求合并器

    /**
     * Find user.
     * @param id the id
     * @return the user
     */
    @HystrixCollapser(batchMethod = "findAll", collapserProperties = {
            // 设置合并时间窗为100毫秒
            @HystrixProperty(name="timerDelayInMilliseconds", value = "100")
    })
    @Override
    public User find(Long id) {
        return restTemplate.getForObject("http://hello-server/users/{1}", User.class, id);
    }

    /**
     * Find all list.
     * @param ids the ids
     * @return the list
     */
    @Override
    @HystrixCommand
    public List<User> findAll(List<Long> ids) {
        return restTemplate.getForObject("http://hello-server/users?ids={1}", List.class, StringUtils.join(ids, ","));
    }


    // endregion
}

package com.example.service.impl;

import com.example.entity.User;
import com.example.service.IUserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Future;

/**
 * UserServiceImpl
 * @author liushun
 * @since JDK 1.8
 **/
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String GROUP_KEY = "UserGroup";
    private static final String THREADPOOL_KEY = "UserByIdThread";

    /**
     * 同步 Gets user by id.
     * ignoreExceptions = {Exception.class} 当方法抛出异常时，Hystrix会将他包装成 HystrixBadRequestException 抛出，这样就不会触发后续的fallback逻辑
     * 线程池划分(建议加`threadPoolKey`) 默认使用 groupKey 划分，如果存在 threadPoolKey 使用 threadPoolKey 进行划分
     * @param id the id
     * @return the user by id
     */
    @Override
    // @HystrixCommand(fallbackMethod = "defaultUser", ignoreExceptions = {Exception.class})
    @HystrixCommand(fallbackMethod = "defaultUser", commandKey = "getUserById", groupKey = GROUP_KEY, threadPoolKey = THREADPOOL_KEY)
    public User getUserById(Long id) {
        return restTemplate.getForObject("http://hello-server/users/{1}", User.class, id);
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
}

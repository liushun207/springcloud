package com.example.command;

import com.example.entity.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

/**
 * UserGetCommand
 * @author liushun
 * @since JDK 1.8
 **/
public class UserGetCommand extends HystrixCommand<User> {
    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");

    private RestTemplate restTemplate;
    private Long id;

    public UserGetCommand(RestTemplate restTemplate, Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet")).andCommandKey(GETTER_KEY));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://hello-server/users/{1}", User.class, id);
    }

    /**
     * 服务降级
     * 同 @HystrixCommand(fallbackMethod = "helloFallback")
     */
    @Override
    protected User getFallback() {
        return new User();
    }

    @Override
    protected String getCacheKey() {
        // 根据id写入缓存
        return String.valueOf(id);
    }

    public static void flushCache(Long id) {
        // 刷新缓存，根据id进行清理
        HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(id));
    }
}

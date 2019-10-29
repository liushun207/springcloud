package com.example.command;

import com.example.entity.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * UserPostCommand
 * @author liushun
 * @since JDK 1.8
 **/
public class UserPostCommand extends HystrixCommand<User> {
    private RestTemplate restTemplate;
    private User user;

    public UserPostCommand(RestTemplate restTemplate, User user) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet")));
        this.restTemplate = restTemplate;
        this.user = user;
    }

    @Override
    protected User run() throws Exception {
        // 写操作
        User r = restTemplate.postForObject("http://hello-server/users", user, User.class);

        // 刷新缓存,清除缓存中的User
        UserGetCommand.flushCache(r.getId());

        return r;
    }

    /**
     * 服务降级
     * 同 @HystrixCommand(fallbackMethod = "helloFallback")
     */
    @Override
    protected User getFallback() {
        return new User();
    }
}
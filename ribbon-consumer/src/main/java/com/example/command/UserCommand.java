package com.example.command;

import com.example.entity.User;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 * UserCommand, 同 `UserServiceImpl`
 * 使用：
 * 同步执行: User u = new UserCommand(restTemplate, 1L).execute();
 * 异步执行: Future<User> futureUser = new UserCommand(restTemplate, 1L).queue();
 * @author liushun
 * @since JDK 1.8
 **/
public class UserCommand extends HystrixCommand<User> {
    private RestTemplate restTemplate;
    private Long id;

    public UserCommand(Setter setter, RestTemplate restTemplate, Long id) {
        super(setter);
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
        return String.valueOf(id);
    }
}

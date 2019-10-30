package com.example.hystrix;

import com.example.entity.User;
import com.example.service.IRefactorHelloService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * HelloService 服务降级
 * @author liushun
 * @since JDK 1.8
 **/
@Component
public class HelloServiceFallback implements IRefactorHelloService {
    /**
     * Hello string.
     * @param name the name
     * @return the string
     */
    @Override
    public String hello(@RequestParam("name") String name) {
        return "error";
    }

    /**
     * Hello user.
     * @param name the name
     * @param age the age
     * @return the user
     */
    @Override
    public User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age) {
        return new User(0L, "未知", 20);
    }

    /**
     * Hello string.
     * @param user the user
     * @return the string
     * @throws Exception the exception
     */
    @Override
    public String hello(@RequestBody User user) {
        return "error";
    }
}

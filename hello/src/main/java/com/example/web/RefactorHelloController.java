package com.example.web;

import com.example.entity.User;
import com.example.service.IHelloService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RefactorHelloController
 * @author liushun
 * @since JDK 1.8
 */
@RestController
public class RefactorHelloController implements IHelloService {

    @Override
    public String hello(@RequestParam String name) {
        return "hello " + name;
    }

    @Override
    public User hello(@RequestHeader String name, @RequestHeader Integer age) {
        return new User(1L, name, age);
    }

    @Override
    public String hello(@RequestBody User user) {
        return "hello " + user.getName() + " ," + user.getAge();
    }
}

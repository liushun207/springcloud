package com.example.service;

import com.example.entity.User;
import org.springframework.web.bind.annotation.*;

/**
 * 公共接口
 * @author liushun
 */
public interface IHelloService {

    /**
     * Hello string.
     * @param name the name
     * @return the string
     */
    @GetMapping("/refactor/hello4")
    String hello(@RequestParam String name);

    /**
     * Hello user.
     * @param name the name
     * @param age the age
     * @return the user
     */
    @GetMapping("/refactor/hello5")
    User hello(@RequestHeader String name, @RequestHeader Integer age);

    /**
     * Hello string.
     * @param user the user
     * @return the string
     * @throws Exception the exception
     */
    @PostMapping("/refactor/hello6")
    String hello(@RequestBody User user);
}

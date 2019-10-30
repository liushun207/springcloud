// package com.example.service;
//
// import com.example.entity.User;
// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.web.bind.annotation.*;
//
// /**
//  *
//  */
// @FeignClient("hello-server")
// public interface IHelloServiceTest {
//     /**
//      * Hello service string.
//      * @return the string
//      */
//     @GetMapping("/hello")
//     String hello();
//
//     /**
//      * Hello string.
//      * @param name the name
//      * @return the string
//      */
//     @GetMapping("/hello1")
//     String hello(@RequestParam String name);
//
//     /**
//      * Hello user.
//      * @param name the name
//      * @param age the age
//      * @return the user
//      */
//     @GetMapping("/hello2")
//     User hello(@RequestHeader String name, @RequestHeader Integer age);
//
//     /**
//      * Hello string.
//      * @param user the user
//      * @return the string
//      * @throws Exception the exception
//      */
//     @PostMapping("/hello3")
//     String hello(@RequestBody User user);
// }

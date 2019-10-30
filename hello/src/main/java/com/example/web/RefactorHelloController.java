package com.example.web;

import com.example.entity.User;
import com.example.service.IHelloService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * RefactorHelloController
 * @author liushun
 * @since JDK 1.8
 */
@RestController
@Log4j2
public class RefactorHelloController implements IHelloService {

    @Override
    public String hello(@RequestParam String name) {

        // 让处理线程等待几秒钟，由于Hystrix默认超时时间是2000，所以采用0到3000的随机数，让处理过程有一定的概率发生超时来触发断路器；
        // int sleepTime = new Random().nextInt(3000);

        // 测试重试机制
        int sleepTime = new Random().ints(1251, 1300).findFirst().getAsInt();
        log.info("sleepTime:" + sleepTime);
        try {
            Thread.sleep(sleepTime);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

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

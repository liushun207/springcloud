package com.example.web;

import com.example.entity.User;
import com.example.service.IHelloServiceTest;
import com.example.service.IRefactorHelloService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConsumerController
 * @author liushun
 * @since JDK 1.8
 **/
@RestController
@Log4j2
public class ConsumerController {
    @Autowired
    private IHelloServiceTest helloServiceTest;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private IRefactorHelloService refactorHelloService;

    @GetMapping(value = "/feign-consumer")
    public String helloConsumer() {
        return helloServiceTest.hello();
    }

    @GetMapping(value = "/feign-consumer2")
    public String helloConsumer2() {
        StringBuilder sb = new StringBuilder();
        sb.append(helloServiceTest.hello()).append("\n");
        sb.append(helloServiceTest.hello("DIDI")).append("\n");
        sb.append(helloServiceTest.hello("DIDI", 30)).append("\n");
        sb.append(helloServiceTest.hello(new User(1L, "DIDI", 30))).append("\n");
        return sb.toString();
    }

    @GetMapping(value = "/feign-consumer3")
    public String helloConsumer3() {
        StringBuilder sb = new StringBuilder();
        // sb.append(refactorHelloService.hello("DIDI")).append("\n");
        // sb.append(refactorHelloService.hello("DIDI", 20)).append("\n");
        sb.append(refactorHelloService.hello(new User(1L, "DIDI", 30))).append("\n");
        log.info(sb.toString());
        return sb.toString();
    }

    @GetMapping(value = "/feign-consumer4")
    public String helloConsumer4() {
        return refactorHelloService.hello("DIDI");
    }
}

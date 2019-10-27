package com.example.service.impl;

import lombok.extern.log4j.Log4j2;
import com.example.service.IHelloSerivce;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * HelloServiceImpl
 * @author liushun
 * @since JDK 1.8
 **/
@Service
@Log4j2
public class HelloServiceImpl implements IHelloSerivce {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback")
    @Override
    public String helloService() {
        long start = System.currentTimeMillis();

        String result = restTemplate.getForEntity("http://hello-server/hello", String.class).getBody();

        long end = System.currentTimeMillis();

        log.info("Spend time:" + (end - start));

        return result;
    }

    public String helloFallback() {
        return "error";
    }
}

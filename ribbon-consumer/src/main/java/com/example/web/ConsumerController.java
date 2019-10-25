package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ConsumerController
 * @author liushun
 * @since JDK 1.8
 **/
@RestController
public class ConsumerController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/ribbon-consumer")
    public String helloConsumer(){
        return restTemplate.getForEntity("http://HELLO-SERVER/hello", String.class).getBody();
    }

}

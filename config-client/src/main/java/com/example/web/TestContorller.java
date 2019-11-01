package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestContorller
 * @author liushun
 * @since JDK 1.8
 **/
@RestController
@RefreshScope
public class TestContorller {

    @Autowired
    private Environment environment;

    @Value("${from}")
    private String from;

    @GetMapping("/from")
    public String from() {
        String propertySources = environment.getProperty("from");

        return from;
    }

}

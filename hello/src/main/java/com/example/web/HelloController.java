package com.example.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * HelloController
 * @author liushun
 * @since JDK 1.8
 **/
@RestController
@Log4j2
public class HelloController {

    @Value("${local.serverId}")
    private String serviceId;

    @Autowired
    private DiscoveryClient client;

    @GetMapping("/hello")
    public String index(){

        List<ServiceInstance> instances = client.getInstances(serviceId);
        ServiceInstance instance = instances.get(0);

        log.info("/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "hello world";
    }
}

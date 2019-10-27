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
import java.util.Random;

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
    public String index() throws Exception {

        List<ServiceInstance> instances = client.getInstances(serviceId);
        ServiceInstance instance = instances.get(0);

        // 让处理线程等待几秒钟，由于Hystrix默认超时时间是2000，所以采用0到3000的随机数，让处理过程有一定的概率发生超时来触发断路器；
        int sleepTime = new Random().nextInt(3000);
        log.info("sleepTime:" + sleepTime);
        Thread.sleep(sleepTime);

        log.info("/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "hello world";
    }
}

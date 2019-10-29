package com.example.web;

import com.example.entity.User;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 测试用例，参数未校验
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
        // int sleepTime = new Random().nextInt(3000);

        int sleepTime = new Random().ints(1251, 1300).findFirst().getAsInt();
        log.info("sleepTime:" + sleepTime);
        Thread.sleep(sleepTime);

        log.info("/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "hello world";
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return new User(id, "张三 - " + id, 20);
    }

    @GetMapping("/users")
    public List<User> getUserById(String ids) {
        if(StringUtils.isBlank(ids)){
            return null;
        }

        String[] split = ids.split(",");
        int len = split.length;

        List<User> users = new ArrayList<>(len);

        for(int i = 0; i < len; i++) {
            String id = split[i];
            User entity = new User(Long.parseLong(id), "张三 - " + id, 20);
            users.add(entity);
        }

        return users;
    }

    @PostMapping("/users")
    public User getUserById(User user) {
        return user;
    }
}

package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

/**
 * @EnableCircuitBreaker 断路器
 * @EnableDiscoveryClient 注册 eureka 客户端
 * @SpringCloudApplication 替换上面3个注解
 */
// @EnableCircuitBreaker
// @EnableDiscoveryClient
// @SpringBootApplication
@SpringCloudApplication
@EnableRetry
public class RibbonApplication {

    /**
     * `LoadBalanced`客户端负载均衡bean
     * @return rest template
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * The entry point of application.
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(RibbonApplication.class, args);
    }

}

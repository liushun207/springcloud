package com.example;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

/**
 * @EnableCircuitBreaker 断路器
 * @EnableDiscoveryClient 注册 eureka 客户端
 * @SpringCloudApplication 替换上面3个注解
 * @EnableHystrixDashboard 启动Hystrix仪表盘
 */
// @EnableCircuitBreaker
// @EnableDiscoveryClient
// @SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
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

    // /**
    //  * hystrix仪表盘
    //  * @return
    //  */
    // @Bean
    // public ServletRegistrationBean getServlet(){
    //     HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
    //     ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
    //     registrationBean.setLoadOnStartup(1);
    //     registrationBean.addUrlMappings("/actuator/hystrix.stream");
    //     registrationBean.setName("HystrixMetricsStreamServlet");
    //     return registrationBean;
    // }
}

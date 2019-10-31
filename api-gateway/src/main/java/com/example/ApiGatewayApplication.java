package com.example;

import com.example.filter.AccessFilter;
import com.example.filter.ApiFilterProcessor;
import com.netflix.zuul.FilterProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@EnableZuulProxy
@SpringCloudApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);

        FilterProcessor.setProcessor(new ApiFilterProcessor());
    }

    /**
     * 自定义错误信息
     * @return
     */
    @Bean
    public DefaultErrorAttributes errorAttributes(){
        return new DefaultErrorAttributes();
    }

    // /**
    //  * 注册过滤器
    //  * @return
    //  */
    // @Bean
    // public AccessFilter accessFilter() {
    //     return new AccessFilter();
    // }

    // /**
    //  * 微服务的命名规则：user-server-v1
    //  * 自动创建类似 /v1/user-server/** 的路由规则
    //  * @return
    //  */
    // @Bean
    // public PatternServiceRouteMapper serviceRouteMapper() {
    //     return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)", "${version}/${name}");
    // }
}

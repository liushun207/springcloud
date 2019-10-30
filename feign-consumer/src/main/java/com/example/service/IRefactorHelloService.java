package com.example.service;

import com.example.hystrix.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 在 @FeignClient 中不能定义相同的名称
 * 如：IHelloServiceTest 中的定义
 * @author liushun
 */
@FeignClient(value = "hello-server", fallback = HelloServiceFallback.class)
public interface IRefactorHelloService extends IHelloService {

}

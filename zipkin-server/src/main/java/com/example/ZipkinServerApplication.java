package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * springboot2.0 以后 推荐使用 官方的 zipkin jar 包运行
 * https://github.com/openzipkin/zipkin/tree/master/zipkin-server
 */
@EnableZipkinServer
@SpringBootApplication
public class ZipkinServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerApplication.class, args);
    }

}

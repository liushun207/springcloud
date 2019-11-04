package com.example.web;

import com.example.rabbit.SinkSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试通过
 * @author liushun
 * @since JDK 1.8
 **/
@Log4j2
@RestController
public class TestController {
    @Autowired
    private SinkSender sinkSender;

    @GetMapping("/sendMessage")
    public String messageWithMQ(@RequestParam String message) {
        sinkSender.output().send(MessageBuilder.withPayload(message).build());
        return "ok";
    }
}

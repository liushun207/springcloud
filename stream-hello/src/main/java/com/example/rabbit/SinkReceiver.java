package com.example.rabbit;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * 消费者
 * EnableBinding 用来指定一个或多个定义了 @Input 或 @Output 注解的接口
 * @author liushun
 * @since JDK 1.8
 **/
@Log4j2
@EnableBinding(value = {Sink.class, SinkSender.class})
public class SinkReceiver {

    /**
     * SendTo 把处理消息发送到 output 通道
     * @param payload
     * @return
     */
    @StreamListener(Sink.INPUT)
    @SendTo(Source.OUTPUT)
    public String receive(Object payload){
        log.info("Receiver: " + payload);
        return payload.toString() + " - 444";
    }

    /**
     * 监听 output 通道
     * @param payload
     */
    @StreamListener(Source.OUTPUT)
    public void receiveFromOutput(Object payload){
        log.info("Received: " + payload);
    }
}

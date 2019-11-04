package com.example.rabbit;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * SinkSender
 * @author liushun
 * @since JDK 1.8
 **/
public interface SinkSender {
    @Output(Source.OUTPUT)
    MessageChannel output();
}

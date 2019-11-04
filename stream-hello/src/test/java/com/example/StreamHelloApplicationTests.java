package com.example;

import com.example.rabbit.SinkSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StreamHelloApplication.class)
@WebAppConfiguration
public class StreamHelloApplicationTests {

    @Autowired
    private SinkSender sinkSender;

    @Test
    public void test() {
        sinkSender.output().send(MessageBuilder.withPayload("From SinkSender").build());
    }

}

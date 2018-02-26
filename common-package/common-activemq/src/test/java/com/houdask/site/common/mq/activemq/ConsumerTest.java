package com.houdask.site.common.mq.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Destination;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerTest {
    @Rule
    public OutputCapture outputCapture = new OutputCapture();
    @Value("${mq.destination.name}")
    private String destinationName ;
    @Autowired
    private ActiveMQDemoProducer producer;

    @Test
    public void contextLoads() throws InterruptedException {
        Destination destination = new ActiveMQQueue(destinationName);
//        for(int i= 0 ; i < 200 ;i++){
            producer.sendMessage(destination, "myname is chhliu!!");
//            Thread.sleep(2000L);
//        }
//
//        assertThat(this.outputCapture.toString().contains("myname is chhliu!!")).isTrue();
    }
}
package com.houdask.site.common.mq.activemq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;  
  
@Component
public class AcitveMQDemoConsumer {

    @Value("${mq.destination.name}")
    private String destinationName ;
        // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息  
    @JmsListener(destination = "${mq.destination.name}")
    public void receiveQueue(String text) {  
        System.out.println("Consumer收到的报文为:"+text);  
    }  
}  
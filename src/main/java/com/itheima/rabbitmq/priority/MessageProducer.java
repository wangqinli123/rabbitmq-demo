package com.itheima.rabbitmq.priority;

import com.itheima.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;

public class MessageProducer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) {

        Connection connection  = null;
        Channel channel = null;
        try{
            connection = ConnectionUtil.getConnection();
            channel = connection.createChannel();

            Map<String, Object> headers = new HashMap<>();
            headers.put("name","gupao");
            headers.put("level","top");

            //对每条消息设置过期时间
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)   //持久化消息
                    .contentEncoding("UTF-8")
                    .expiration("10000")
                    .headers(headers)   //自定义属性
                    .priority(5)     //优先级，默认是5，配合队列的x-max-priority使用
                    .build();

            //声明队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            String msg = "Hello world,RabbitMQ ttl test";

            //发送消息
            channel.basicPublish("",QUEUE_NAME,properties,msg.getBytes());

            channel.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

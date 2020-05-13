package com.itheima.rabbitmq.ttl;

import com.itheima.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class TTLProducer {

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = ConnectionUtil.getConnection();
            //创建信息通道
            channel = connection.createChannel();

            //通过队列属性设置消息过期时间
            Map<String, Object> argss = new HashMap<>();
            argss.put("x-message-ttl",6000);

            //声明队列
            channel.queueDeclare("myQueue",true,false,false,argss);

            String msg = "Hello world,RabbitMQ ttl test";
            //发送消息（发送到默认交换机AMQP Default，Direct）
            //如果有一个队列名称跟Routing Key相等，那么消息会路由到这个队列
            channel.basicPublish("my-exchange","routingKey_ttl",null,msg.getBytes());

            channel.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

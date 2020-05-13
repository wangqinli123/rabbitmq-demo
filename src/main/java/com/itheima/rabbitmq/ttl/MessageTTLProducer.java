package com.itheima.rabbitmq.ttl;

import com.itheima.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class MessageTTLProducer {

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = ConnectionUtil.getConnection();
            //创建信息通道
            channel = connection.createChannel();

            //声明队列
            channel.queueDeclare("myQueue_ttl",true,false,false,null);

            //对每条消息设置过期时间
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)   //持久化消息
                    .contentEncoding("UTF-8")
                    .expiration("10000")
                    .build();

            String msg = "Hello world,RabbitMQ ttl test";
            //发送消息（发送到默认交换机AMQP Default，Direct）
            //如果有一个队列名称跟Routing Key相等，那么消息会路由到这个队列
            channel.basicPublish("my-exchange-ttl","routingKey_ttl",properties,msg.getBytes());

            channel.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
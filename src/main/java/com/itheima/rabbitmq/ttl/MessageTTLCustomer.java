package com.itheima.rabbitmq.ttl;

import com.itheima.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MessageTTLCustomer {

    public static void main(String[] args) {

        Connection connection = null;
        Channel channel = null;
        try {
            //创建连接
            connection= ConnectionUtil.getConnection();
            //创建信息通道
            channel = connection.createChannel();

            //声明交换机
            channel.exchangeDeclare("my-exchange-ttl","direct",false,false,null);

            //声明队列
            channel.queueDeclare("myQueue_ttl",true,false,false,null);
            System.out.println("Waiting for message...");

            //绑定队列和交换机
            channel.queueBind("myQueue_ttl","my-exchange-ttl","routingKey_ttl");

            //创建消费者
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "UTF-8");
                    System.out.println("Received message: " + msg);
                    System.out.println("consumerTag: " + consumerTag);
                    System.out.println("DeliveryTag: " + envelope.getDeliveryTag());
                }
            };

            //开始获取消息
            int sleep = 15;
            System.out.println("customer sleep " +sleep);
            TimeUnit.SECONDS.sleep(sleep);
            channel.basicConsume("myQueue_ttl",true,consumer);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

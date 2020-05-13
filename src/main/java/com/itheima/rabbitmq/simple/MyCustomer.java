package com.itheima.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

public class MyCustomer {

    private final static String QUEUE_NAME = "SIMPLE_NAME";
    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";

    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //连接IP
        factory.setHost("192.168.1.108");
        //连接端口
        factory.setPort(5672);
        //创建虚拟机
        factory.setVirtualHost("/");
        //用户,密码
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = null;
        Channel channel = null;
        try {
            //创建连接
            connection = factory.newConnection();
            //创建信息通道
            channel = connection.createChannel();

            //声明交换机
            /**
             * String exchange,
             * String type,
             * boolean durable,
             * boolean autoDelete,
             * Map<String, Object> arguments
             */
            channel.exchangeDeclare(EXCHANGE_NAME,"direct",false,false,null);
            //声明队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            System.out.println("Waiting for message...");

            //绑定队列和交换机
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"simple_rabbitmq");

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
            channel.basicConsume(QUEUE_NAME,true,consumer);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

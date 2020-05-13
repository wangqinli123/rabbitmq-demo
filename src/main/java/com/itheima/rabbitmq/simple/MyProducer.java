package com.itheima.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MyProducer {

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

            //message
            String msg = "Hello world,Rabbit MQ";
            //声明队列
            /**
             * String queue: 指定queue名字
             * boolean durable:是否持久化
             * boolean exclusive:
             * 是否排外的，有两个作用，
             * 1：当连接关闭时connection.close()该队列是否会自动删除；
             * 2：该队列是否是私有的private，如果不是排外的，可以使用两个消费者都访问同一个队列，没有任何问题，
             * 如果是排外的，会对当前队列加锁，其他通道channel是不能访问的，如果强制访问会报异常,
             * 一般等于true的话用于一个队列只能有一个消费者来消费的场景
             * boolean autoDelete:是否自动删除,当最后一个消费者断开连接之后队列是否自动被删除,可以通过RabbitMQ Management,
             * 查看某个队列的消费者数量,当consumers = 0时队列就会自动删除
             * Map<String, Object> arguments:配置参数
             */
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            //发送消息（发送到默认交换机AMQP Default，Direct）
            //如果有一个队列名称跟Routing Key相等，那么消息会路由到这个队列
            /**
             * @param exchange:将消息发送到交换机,默认发送到AMQP
             * @param routingKey the routing key
             * @param props 属性列表
             * @param body the message body
             */
            channel.basicPublish(EXCHANGE_NAME,"simple_rabbitmq",null,msg.getBytes());

            channel.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

package com.itheima.rabbitmq.dlx;

import com.itheima.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class DlxProducer {

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try{

            connection = ConnectionUtil.getConnection();
            channel = connection.createChannel();

            //对每条消息设置过期时间
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)   //持久化消息
                    .contentEncoding("UTF-8")
                    .expiration("10000")
                    .build();
            String msg = "Hello world,RabbitMQ ttl test";

            //发送消息
            for (int i=0;i<10;i++){
                channel.basicPublish("","TEXT_DLX_QUEUE",properties,msg.getBytes());
            }

            channel.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

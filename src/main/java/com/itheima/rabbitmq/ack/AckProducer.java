package com.itheima.rabbitmq.ack;

import com.itheima.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class AckProducer {
    private final static String QUEUE_NAME = "TEST_ACK_QUEUE";

    public static void main(String[] args) throws Exception {

        // 建立连接
        Connection connection = ConnectionUtil.getConnection();
        // 创建消息通道
        Channel channel = connection.createChannel();

        String msg = "test ack message ";
        // 声明队列（默认交换机AMQP default，Direct）
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 发送消息
        for (int i =0; i<5; i++){
            channel.basicPublish("", QUEUE_NAME, null, (msg+i).getBytes());
        }

        channel.close();
        connection.close();
    }
}


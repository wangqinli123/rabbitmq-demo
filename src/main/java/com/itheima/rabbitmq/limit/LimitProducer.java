package com.itheima.rabbitmq.limit;

import com.itheima.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 用于测试消费者限流
 */
public class LimitProducer {
    private final static String QUEUE_NAME = "TEST_LIMIT_QUEUE";

    public static void main(String[] args) throws Exception {

        // 建立连接
        Connection conn = ConnectionUtil.getConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        String msg = "a limit message ";
        // 声明队列（默认交换机AMQP default，Direct）
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 发送消息
        for(int i=0; i<100; i++){
            channel.basicPublish("", QUEUE_NAME, null, (msg+i).getBytes());
        }

        channel.close();
        conn.close();
    }
}


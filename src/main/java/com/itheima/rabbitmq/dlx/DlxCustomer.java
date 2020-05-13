package com.itheima.rabbitmq.dlx;

import com.itheima.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 死信交换机，死信队列
 */
public class DlxCustomer {

    public static void main(String[] args) {

        Connection connection = null;
        Channel channel = null;
        try {
            connection = ConnectionUtil.getConnection();
            channel = connection.createChannel();

            //指定队列的死信交换机
            Map<String, Object> argss = new HashMap<>();
            //
            argss.put("x-dead-letter-exchange","DLX-EXCHANGE");
            //设置队列的TTL值
            //argss.put("x-expires","9000");
            //如果设置了队列的最大长度，超过长度时，先入队的消息会被发送到DLX
            //argss.put("x-max-length",4);

            //声明队列
            channel.queueDeclare("TEXT_DLX_QUEUE",false,false,false,argss);
            //声明死信交换机
            channel.exchangeDeclare("DLX_EXCHANGE","topic",false,false,false,null);
            //声明死信队列
            channel.queueDeclare("DLX_QUEUE",false,false,false,null);
            //绑定
            channel.queueBind("TEXT_DLX_QUEUE","DLX_EXCHANGE","#");
            System.out.println("Waiting for message...");

            //创建消费者
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "UTF-8");
                    System.out.println("Received message: " + msg);
                }
            };

            //开始获取消息
            channel.basicConsume("TEXT_DLX_QUEUE",true,consumer);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

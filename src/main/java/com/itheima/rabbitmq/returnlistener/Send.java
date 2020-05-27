package com.itheima.rabbitmq.returnlistener;

import com.itheima.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Send {

    private final static String EXCHANGE_NAME = "";

    public static void main(String[] args) throws Exception {

        //创建连接
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText,
                                     String exchange, String routingKey,
                                     AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("=========handleReturn===method============");
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routingKey:"+routingKey);
                System.out.println("message:"+new String(body));
            }
        });

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .build();

        //第三个参数是设置的mandatory
        channel.basicPublish(EXCHANGE_NAME,"",true,properties,"注册验证码".getBytes());

        TimeUnit.SECONDS.sleep(2);

        channel.close();
        connection.close();

    }
}

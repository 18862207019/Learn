package com.dada.tx;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxRecv {

    private static final String QUEUE_NAME = "TXQUUE";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 获取一个连接
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中获取一个通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 消费者
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });



    }
}

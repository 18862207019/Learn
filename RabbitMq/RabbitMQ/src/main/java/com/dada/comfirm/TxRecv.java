package com.dada.comfirm;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxRecv {

    private  static  final String Queue_name = "test_queue_confirm_1";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 获取一个连接
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中获取一个通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(Queue_name, false, false, false, null);

        // 消费者
        channel.basicConsume(Queue_name, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });

    }
}

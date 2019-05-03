package com.dada.pb;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {

    private static final  String QUEUE_NAME="test_Queue";

    private static  final  String exchangeName = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        // 绑定到交换机或者转发器
        channel.queueBind(QUEUE_NAME,exchangeName,"");

        channel.basicQos(1);

        // 定义一个消费者
        Consumer consumer = new DefaultConsumer(channel) {
            // 消息到达触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[2] Recv msg  ::::   z"+msg);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    // 手动应答rabiitMQ 队列
                    channel.basicAck(envelope.getDeliveryTag(),false);
                    System.out.println("`2 [down]");
                }
            }
        };

        boolean autoAck= false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}

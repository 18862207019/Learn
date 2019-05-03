package com.dada.workfair;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {

    private  static  final String Queue_name = "test_work_quequefair";


    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Queue_name,false,false,false,null);

        // 设置一次只接收一个队列的消息
        channel.basicQos(1);

        // 定义一个消费者
        Consumer consumer = new DefaultConsumer(channel) {

            // 消息到达触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[1] Recv msg  ::::   z"+msg);
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                    // 手动应答rabiitMQ 队列
                    channel.basicAck(envelope.getDeliveryTag(),false);
                    System.out.println("`1 [down]");
                }
            }
        };

        // 自动应答fasle
        boolean autoAck= false;
        channel.basicConsume(Queue_name,autoAck,consumer);
    }
}
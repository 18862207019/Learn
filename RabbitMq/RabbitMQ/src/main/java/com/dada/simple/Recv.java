package com.dada.simple;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  消费者消费消息
 */
public class Recv {

    private  static  final String Queue_name = "test_simple_queque";


    public static void main(String[] args) throws IOException, TimeoutException {

        //  获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(Queue_name,false,false,false,null);

        // 声明监听对象
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             * 一旦有消息进入队列  就会触发这个方法
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body   数据
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println(msg);
            }
        };

        // 监听队列
        channel.basicConsume(Queue_name,consumer);


    }
}

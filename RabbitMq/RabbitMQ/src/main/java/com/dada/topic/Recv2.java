package com.dada.topic;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    private static final String EXCHANGE_NAME="TESTECXCHANGE_Topic";

    private  static  final String Queue_name = "test_queque_topic2";


    public static void main(String[] args) throws IOException, TimeoutException {

        //  获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(Queue_name,false,false,false,null);

        // 设置手动提交
        channel.basicQos(1);

        //绑定队列
        channel.queueBind(Queue_name,EXCHANGE_NAME,"goods.#");


        // 定义一个消费者
        Consumer consumer = new DefaultConsumer(channel) {
            // 消息到达触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[2] Recv msg  :::: "+msg);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    // 手动应答rabiitMQ 队列
                    channel.basicAck(envelope.getDeliveryTag(),false);
                    System.out.println("`【2】 down");
                }
            }
        };

        boolean autoAck= false;
        channel.basicConsume(Queue_name,autoAck,consumer);
    }
}

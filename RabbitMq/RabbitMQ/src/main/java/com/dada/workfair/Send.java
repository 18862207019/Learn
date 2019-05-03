package com.dada.workfair;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列
 */
public class Send {

    private  static  final String Queue_name = "test_work_quequefair";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        // 获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 获取channel
        Channel channel = connection.createChannel();

        // 声明一个队列
        channel.queueDeclare(Queue_name,false,false,false,null);

        /**
         * 每个消费者发送确认消息之前 消息队列不发送下一个消息到消费者，一次只处理一个消息
         * 限制发送给同一个消费者，不超过一个消息
         */
        int  prefetchCount=1;
        channel.basicQos(prefetchCount);

        // 发送50条消息
        for (int i=0;i<50 ; i++){
            String msg= " hello"+i;
            System.out.println("send======="+msg);
            channel.basicPublish("",Queue_name,null,msg.getBytes());
            Thread.sleep(i*20);
        }

        channel.close();
        connection.close();
    }
}

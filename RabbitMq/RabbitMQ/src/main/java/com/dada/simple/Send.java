package com.dada.simple;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 *  生产者
 */
public class Send {

    private  static  final String Queue_name = "test_simple_queque";

    public static void main(String[] args) throws  Exception {

        // 获取一个连接
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中获取一个通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(Queue_name,false,false,false,null);

        // 发送的消息
        String msg= "hello Simple";

        // 发送
        channel.basicPublish("",Queue_name,null,msg.getBytes());

        System.out.println("----send msg:" + msg);

        //关闭连接
        channel.close();
        connection.close();

    }
}

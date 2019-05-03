package com.dada.pb;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static  final  String exchangeName = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(exchangeName,"fanout");// 分发

        // 发送消息
        String msg = "hello  ps";

        channel.basicPublish(exchangeName,"",null,msg.getBytes());

        System.out.println("sned "+msg );

        channel.close();

        connection.close();

    }
}

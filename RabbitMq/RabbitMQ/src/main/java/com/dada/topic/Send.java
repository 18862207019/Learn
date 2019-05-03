package com.dada.topic;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME="TESTECXCHANGE_Topic";

    public static void main(String[] args) throws IOException, TimeoutException {

        //  获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明Exchangge
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        // 消息
        String msg="商品。。。。。。。。";

        // 路由key
        String routerkey="goods.delete";

        // 发送消息
        channel.basicPublish(EXCHANGE_NAME,routerkey,null,msg.getBytes());

        //打印
        System.out.println("msg："+routerkey);

        // 关闭资源
        channel.close();
        connection.close();
    }
}

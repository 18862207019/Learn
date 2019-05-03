package com.dada.tx;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSend {

    private static final String QUEUE_NAME = "TXQUUE";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 获取一个连接
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中获取一个通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "Hello TX";

        // 开启事务模式
        channel.txSelect();
        try {
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

//            测试异常回滚
//            int x = 1/0;

            channel.txCommit();
        }catch (Exception e){
            channel.txRollback();
            System.out.println("QUEUE Rollback");
        }

        channel.close();
        connection.close();
    }
}

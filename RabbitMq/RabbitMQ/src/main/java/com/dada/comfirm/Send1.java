package com.dada.comfirm;

import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 单条
 */
public class Send1 {

    private  static  final String Queue_name = "test_queue_confirm_1";

    public static void main(String[] args) throws  Exception {

        // 获取一个连接
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中获取一个通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(Queue_name,false,false,false,null);

        // 设置为confirm模式
        channel.confirmSelect();

        // 发送的消息
        String msg= "hello confirm";

        // 发送
        channel.basicPublish("",Queue_name,null,msg.getBytes());

        if (!channel.waitForConfirms()){
            System.out.println("send fail");
        }else {
            System.out.println("----send msg:" + msg);
        }

        //关闭连接
        channel.close();
        connection.close();

    }
}

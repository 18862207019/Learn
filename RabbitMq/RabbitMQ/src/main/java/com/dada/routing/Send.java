package com.dada.routing;
import com.dada.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME="TESTECXCHANGE_DIRECT";

    public static void main(String[] args) throws IOException, TimeoutException {

        //  获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明Exchangge
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        // 消息
        String msg="hello direct";

        // 路由key
//        String routerkey="error";

        String routerkey="info";

        // 发送消息
        channel.basicPublish(EXCHANGE_NAME,routerkey,null,msg.getBytes());

        // 关闭资源
        channel.close();
        connection.close();
    }
}

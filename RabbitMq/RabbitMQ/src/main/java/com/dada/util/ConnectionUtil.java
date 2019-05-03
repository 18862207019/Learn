package com.dada.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.AMQImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 连接工厂
 */
public class ConnectionUtil {

    /**
     * 获取Mq的连接
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public  static Connection  getConnection() throws IOException, TimeoutException {

        // 定义一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 设置服务地址
        connectionFactory.setHost("127.0.0.1");

        // 设置端口号
        connectionFactory.setPort(5672);

        // 设置数据库 Vhost
        connectionFactory.setVirtualHost("/vhostnew");

        // 设置用户名密码
        connectionFactory.setUsername("dada");
        connectionFactory.setPassword("dada");

        // 获取连接
        Connection connection = connectionFactory.newConnection();

        return connection;
    }
}

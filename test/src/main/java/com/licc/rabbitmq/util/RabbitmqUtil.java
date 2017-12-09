package com.licc.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.licc.rabbitmq.common.ConstantValue;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitmqUtil {
	
	private static ConnectionFactory factory = null;
	
	private static ConnectionFactory getConnectionFactory() {
		if(factory == null) {
			factory = new ConnectionFactory();
			factory.setUsername(ConstantValue.USER);
			factory.setPassword(ConstantValue.PASS);
			factory.setHost(ConstantValue.IP);
			factory.setVirtualHost(ConstantValue.HOST);
			factory.setPort(ConstantValue.PORT);
		}
		return factory;
	}
	
	public static Connection createConnection() throws IOException, TimeoutException {
		Connection connection = getConnectionFactory().newConnection();
		return connection;
	}
}

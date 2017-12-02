package com.licc.rabbitmq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerMsg {
	
	
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ProducerMsg producer = new ProducerMsg();
		
		ConnectionFactory factory = producer.createConnectionFactory();
		
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		String exchangeName = "testExchange";
		String queueName = "testqueue";
		String exchangeType = "direct";
		
		//  声明一个exchange
		channel.exchangeDeclare(exchangeName, exchangeType);
		//  声明一个queue
		//channel.queueDeclare(queueName, false, false, false, null);
		//  声明一个Binding 绑定exchange到queue的路径
		String routingKey = "routingKey";
		//channel.queueBind(queueName, exchangeName, routingKey);
		//  发布消息到exchange中
		channel.basicPublish(exchangeName, routingKey, null, "hello world".getBytes("UTF-8"));
		
		channel.close();
		connection.close();
		
		
		while(true) {
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	
	public ConnectionFactory createConnectionFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(ConstantValue.USER);
		factory.setPassword(ConstantValue.PASS);
		factory.setHost(ConstantValue.IP);
		factory.setVirtualHost(ConstantValue.HOST);
		factory.setPort(ConstantValue.PORT);
		return factory;
	}
	
	
	
	
	
	
}

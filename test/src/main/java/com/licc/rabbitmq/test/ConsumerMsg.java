package com.licc.rabbitmq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConsumerMsg {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConsumerMsg consumerMsg = new ConsumerMsg();
		
		ConnectionFactory factory = consumerMsg.createConnectionFactory();
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchangeName = "testExchange";
		String queueName = "testqueue";
		String exchangeType = "direct";
		
		//  声明一个exchange
		//channel.exchangeDeclare(exchangeName, exchangeType);
		//  声明一个queue
		channel.queueDeclare(queueName, false, false, false, null);
		//  声明一个Binding 绑定exchange到queue的路径
		String routingKey = "routingKey";
		channel.queueBind(queueName, exchangeName, routingKey);
		
		//  订阅的方式获取数据
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
		
		
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

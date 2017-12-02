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

/**
 * Hello world!
 *
 */
public class App {
	private final static String QUEUE_NAME = "hello";
	static boolean isBreak = false;

	public static void main(String[] args) {
		System.out.println("Hello World!");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(ConstantValue.USER);
		factory.setPassword(ConstantValue.PASS);
		factory.setHost(ConstantValue.IP);
		factory.setVirtualHost(ConstantValue.HOST);
		factory.setPort(5672);
		try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			
			
			String message = "Hello World!";
			
			
			
			System.out.println(" [x] Sent '" + message + "'");
			
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(" [x] Received '" + message + "'");
					//  isBreak = true;
				}
			};
			channel.basicConsume(QUEUE_NAME, true, consumer);

			
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			
			while (!isBreak) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}

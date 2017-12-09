package com.licc.rabbitmq.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.licc.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class ConsumerMsg {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = RabbitmqUtil.createConnection();
		Channel channel = connection.createChannel();
		
		String exchangeName = "testExchange";
		String queueName = "testqueue";
		
		//  声明一个queue
		channel.queueDeclare(queueName, false, false, false, null);
		//  声明一个Binding 绑定exchange到queue的路径
		String routingKey = "routingKey";
		channel.queueBind(queueName, exchangeName, routingKey);
		
		QueueingConsumer consumer = new QueueingConsumer(channel);

		channel.basicConsume(queueName, true, consumer);
		
		while(true) {
			try {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				System.out.println(" [x] Received '" + message + "'");
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		//channel.close();
		//connection.close();
	}
	
}

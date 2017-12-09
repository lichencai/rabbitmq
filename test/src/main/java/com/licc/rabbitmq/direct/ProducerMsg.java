package com.licc.rabbitmq.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.licc.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

public class ProducerMsg {
	
	
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = RabbitmqUtil.createConnection();
		
		Channel channel = connection.createChannel();
		
		String exchangeName = "testExchange1";
		String queueName = "testqueue1";
		String exchangeType = "direct";
		
		//  声明一个exchange
		channel.exchangeDeclare(exchangeName, exchangeType, true);
		//  声明一个queue 生产者和消费者都可以创建queue 但两个queue的参数必须一样  这样才回保证幂等
		channel.queueDeclare(queueName, true, false, false, null);
		//  声明一个Binding 绑定exchange到queue的路径
		String routingKey = "routingKey";
		channel.queueBind(queueName, exchangeName, routingKey);
		//  发布消息到exchange中  MessageProperties.PERSISTENT_TEXT_PLAIN, 设置为持久化消息
		channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, "hello world".getBytes("UTF-8"));
		
		channel.close();
		connection.close();
	}
}

package com.licc.rabbitmq.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.licc.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class ProducerMsg {
	
	
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = RabbitmqUtil.createConnection();
		
		Channel channel = connection.createChannel();
		
		String exchangeName = "testExchange";
		String queueName = "testqueue";
		String exchangeType = "direct";
		
		//  声明一个exchange
		channel.exchangeDeclare(exchangeName, exchangeType);
		//  声明一个queue 生产者和消费者都可以创建queue 但两个queue的参数必须一样  这样才回保证幂等
		channel.queueDeclare(queueName, false, false, false, null);
		//  声明一个Binding 绑定exchange到queue的路径
		String routingKey = "routingKey";
		channel.queueBind(queueName, exchangeName, routingKey);
		//  发布消息到exchange中
		channel.basicPublish(exchangeName, routingKey, null, "hello world".getBytes("UTF-8"));
		
		channel.close();
		connection.close();
	}
}

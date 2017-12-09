package com.licc.rabbitmq.topics;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.licc.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TopicsProducer {
	private static final String EXCHANGE_NAME = "topic_logs";
	private static Connection connection = null;

	public static void main(String[] args) throws IOException, TimeoutException {
		
		//  选择键会被路由到指定绑定规则的queue中，并且所有的queue都会接收到请求，这点和direct类型的不一样，direct类型的只能有一个queue接收到消息
		String[] argv = {"licc.123123","topic发送消息"};
		
		connection = RabbitmqUtil.createConnection();

		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		String routingKey = argv[0];
		String message = argv[1];

		channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
		System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
		
		//connection.close();
		//channel.close();
	}
}

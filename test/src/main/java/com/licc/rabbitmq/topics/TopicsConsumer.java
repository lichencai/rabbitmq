package com.licc.rabbitmq.topics;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.licc.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class TopicsConsumer {
	private static final String EXCHANGE_NAME = "topic_logs";
	private static Connection connection = null;

	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
		//  绑定键必须有按照。进行分割   *匹配一个单词，#匹配0个或多个单词。
		String[] argv = {"licc.*.#"};
		
		connection = RabbitmqUtil.createConnection();

		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queueName = channel.queueDeclare().getQueue();

		if (argv.length < 1) {
			System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
			System.exit(1);
		}
		
		//  这个queue可以绑定多个routingKey 接收不同exchange发过来的消息
		for (String bindingKey : argv) {
			channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
		}

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			String routingKey = delivery.getEnvelope().getRoutingKey();
			System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");
		}
	}

}

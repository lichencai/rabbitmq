package com.licc.rabbitmq.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.licc.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class FanoutConsumer {

	private static final String EXCHANGE_NAME = "logs";

	private static Connection connection = null;

	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		connection = RabbitmqUtil.createConnection();
		Channel channel = connection.createChannel();

		// 声明一个queue并且获取到该queue的name
		String queueName = channel.queueDeclare().getQueue();
		// 注意routingKey必须为"",表示接受所有消息，如果routingKey不为"",则会被fanout类型的exchange忽略掉
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
		}

	}

}

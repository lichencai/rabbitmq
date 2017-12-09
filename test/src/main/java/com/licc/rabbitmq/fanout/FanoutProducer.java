package com.licc.rabbitmq.fanout;

import com.licc.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class FanoutProducer {
	
	private static final String EXCHANGE_NAME = "logs";
	
	private static Connection connection = null;
	
	public static void main(String[] args) throws Exception{
		
		connection = RabbitmqUtil.createConnection();
		
		Channel channel = connection.createChannel();
		//  声明一个广播的exchange 如果有queue绑定到该exchange上面，有message时，会发送到各个queue上面
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
		
		String message = "hello 广播消息";
		
		//  注意routingKey的设置为空字符串
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		
		System.out.println(" [x] Sent '" + message + "'");
		
		channel.close();
	    connection.close();
	}
	
	
}

package com.licc.netty.channelHandlerContextTest;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel>{

	private TestServerHandler handler = null;
	
	public MyChannelInitializer() {
		
	}
	
	public MyChannelInitializer(TestServerHandler handler) {
		this.handler = handler;
	}
	
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		
		
		System.out.println(ch.remoteAddress() + " 连接上了.");
		
//		ch.pipeline().addLast("myhandler1", new TestServerHandler1());
		ch.pipeline().addLast("myhandler", new TestServerHandler());
	}

}

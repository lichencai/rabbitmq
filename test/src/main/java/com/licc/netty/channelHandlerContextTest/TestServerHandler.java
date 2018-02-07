package com.licc.netty.channelHandlerContextTest;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;

//@Sharable
public class TestServerHandler extends ChannelInboundHandlerAdapter {

	private final AttributeKey<Integer> counter = AttributeKey.valueOf("counter");
	
	private int i = 0;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		System.out.println("i is : " + i++);
		System.out.println("TestServerHandler channelActive : " + ch.remoteAddress());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel ch = ctx.channel();
		System.out.println("TestServerHandler channelRead : " + ch.remoteAddress());
		if(msg instanceof Integer) {
			System.out.println("msg type is integer");
		}else {
			System.out.println("msg type is : " + msg.toString());
		}
		
		Integer a = ctx.channel().attr(counter).get();
		if (a == null) {
			a = 1;
		}else {
			ctx.channel().attr(counter).set(a * a);
		}
		System.out.println("========================" + a);
		
		
//		System.out.println("===================================" + (Integer) msg);
//		ctx.channel().attr(counter).set(a * (Integer) msg);
	}
	
	
	

}

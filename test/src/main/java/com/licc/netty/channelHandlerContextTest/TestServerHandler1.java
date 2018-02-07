package com.licc.netty.channelHandlerContextTest;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TestServerHandler1 extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		System.out.println("TestServerHandler1 channelActive : " + ch.remoteAddress());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel ch = ctx.channel();
		System.out.println("TestServerHandler1 channelRead : " + ch.remoteAddress());
	}
	
}

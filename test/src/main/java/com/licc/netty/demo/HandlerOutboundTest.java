package com.licc.netty.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class HandlerOutboundTest extends ChannelOutboundHandlerAdapter{

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println("===========HandlerOutboundTest write==============");
		super.write(ctx, msg, promise);
	}
	
}

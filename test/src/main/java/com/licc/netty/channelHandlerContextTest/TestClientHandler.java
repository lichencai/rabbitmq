package com.licc.netty.channelHandlerContextTest;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TestClientHandler extends ChannelInboundHandlerAdapter {
	@Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
		Channel ch = ctx.channel();
		System.out.println("client active channel : " + ch.remoteAddress());
    }
}

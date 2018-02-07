package com.licc.netty.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 编写处理器
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
	
	private static Map<Integer, ChannelHandlerContext> USER_CHANNEL_MAP  = new HashMap<>();
	 
	private static AtomicInteger atomic = new AtomicInteger();
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		int id = atomic.incrementAndGet();
		USER_CHANNEL_MAP.put(id, ctx);
		System.out.println("socket is registered : " + id);
	}

	//  每当从客户端收到新的数据时，这个方法会在收到消息时被调用
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
			ByteBuf in = (ByteBuf) msg;
		    try {
		        while (in.isReadable()) { // (1)
		            System.out.print((char) in.readByte());
		            System.out.flush();
		        }
		        ctx.writeAndFlush(msg);
		    } finally {
//		        ReferenceCountUtil.release(msg); // (2)
		    }
	}
	
	//  事件处理方法是当出现Throwable对象才会被调用
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
	
	
}

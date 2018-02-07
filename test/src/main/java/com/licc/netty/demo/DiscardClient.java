package com.licc.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class DiscardClient {
	
	
	public void run() throws Exception{
		String host = "192.168.108.108";// args[0];
        int port = 8081;//Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new DiscardClientHandler());
                }
            });
            // 启动客户端
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
	}
	
	
	public static void main(String[] args) throws Exception{
		
		
		
		
		for(int i = 0; i < 1000; i++) {
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					DiscardClient client = new DiscardClient();
					try {
						client.run();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			t.start();
			
		}
	}
}

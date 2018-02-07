package com.licc.netty.channelHandlerContextTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TestClient {
	public static void main(String[] args) throws Exception {
		String host = "127.0.0.1";// args[0];
        int port = 8081;//Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TestClientHandler());
                }
            });
            // 启动客户端
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            Channel channel = f.channel();
            
            final ByteBuf time = channel.alloc().buffer(4); // (2)
            time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
            
            channel.writeAndFlush(time);
            /*
             * 下面这样是发送不出去的
            channel.writeAndFlush(new Integer(3));
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
            	String tmp = in.readLine();
                channel.writeAndFlush(tmp.getBytes());
            }
            */
            
            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
	}
}

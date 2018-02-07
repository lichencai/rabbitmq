package com.licc.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class DiscardServer {
	private int port;
    
    public DiscardServer(int port) {
        this.port = port;
    }
    
    public void run() throws Exception {
    	
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // 用来处理I/O操作的多线程事件循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // 启动NIO服务的辅助启动类
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // 指定使用NioServerSocketChannel类来举例说明一个新的Channel如何接收进来的连接
             .childHandler(new ChannelInitializer<SocketChannel>() { // 事件处理类
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new DiscardServerHandler());
              }
             })   
             .option(ChannelOption.SO_BACKLOG, 128)          // 指定的通道实现的配置参数
             .childOption(ChannelOption.SO_KEEPALIVE, true); // option()是提供给NioServerSocketChannel用来接收进来的连接。childOption()是提供给由父管道ServerChannel接收到的连接，在这个例子中也是NioServerSocketChannel
            ChannelFuture f = b.bind(port).sync(); // 绑定端口
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8081;
        }
        new DiscardServer(port).run();
    }
}

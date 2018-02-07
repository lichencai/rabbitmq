package com.licc.netty.channelHandlerContextTest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
	
	private int port;
    
    public TestServer(int port) {
        this.port = port;
    }
    
    public void run() throws Exception{
    	EventLoopGroup bossGroup = new NioEventLoopGroup(); // 用来处理I/O操作的多线程事件循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	
        	TestServerHandler handler = new TestServerHandler();
        	
            ServerBootstrap b = new ServerBootstrap(); // 启动NIO服务的辅助启动类
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // 指定使用NioServerSocketChannel类来举例说明一个新的Channel如何接收进来的连接
             .childHandler(new MyChannelInitializer())
             .option(ChannelOption.SO_BACKLOG, 128)          // 指定的通道实现的配置参数
             .childOption(ChannelOption.SO_KEEPALIVE, true); // option()是提供给NioServerSocketChannel用来接收进来的连接。childOption()是提供给由父管道ServerChannel接收到的连接，在这个例子中也是NioServerSocketChannel
            
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // 绑定端口
    
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception{
    	int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8081;
        }
        new TestServer(port).run();
	}
    
    
}

package com.tan.netty.usecase.codecs.delimiter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class EnchoServer {
 public void serve(int port ){
	 EventLoopGroup workerGroup=new NioEventLoopGroup();
	 EventLoopGroup bossGroup=new NioEventLoopGroup();
	 try {
		 ServerBootstrap b=new ServerBootstrap();
		 b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
		.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ByteBuf deilimiter= Unpooled.copiedBuffer("$_".getBytes());
				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,deilimiter));
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new EnchoServerHandler());
			}
		
			
		});
		 ChannelFuture f= b.bind(port).sync();
		 f.channel().closeFuture().sync();
	} catch (InterruptedException e) {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
	 
 }
 
 public static void main(String[] args) {
	 EnchoServer timerServer=new EnchoServer();
	 timerServer.serve(8080);
}
}

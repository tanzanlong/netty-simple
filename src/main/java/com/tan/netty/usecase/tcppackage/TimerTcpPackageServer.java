package com.tan.netty.usecase.tcppackage;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class TimerTcpPackageServer {
 public void serve(int port ){
	 EventLoopGroup workerGroup=new NioEventLoopGroup();
	 EventLoopGroup bossGroup=new NioEventLoopGroup();
	 try {
		 ServerBootstrap b=new ServerBootstrap();
		 b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
		 .childHandler(new ServerTcpPackageHandler());
		 ChannelFuture f= b.bind(port).sync();
		 f.channel().closeFuture().sync();
	} catch (InterruptedException e) {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
	 
 }
 
 public static void main(String[] args) {
	 TimerTcpPackageServer timerServer=new TimerTcpPackageServer();
	 timerServer.serve(8080);
}
}

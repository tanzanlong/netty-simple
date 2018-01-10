package com.tan.netty.usecase.tcppackage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimerTcpPackageClient {
	
	public void serve(int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new ClientTcpPackageHandler());
						}

					});
			ChannelFuture f = b.connect("127.0.0.1", port).sync();
			f.channel().closeFuture().sync();
			f.channel().writeAndFlush("query timer order");
		} catch (InterruptedException e) {
			group.shutdownGracefully();
		}

	}
	
	 public static void main(String[] args) {
		 TimerTcpPackageClient timerClient=new TimerTcpPackageClient();
		 timerClient.serve(8080);
	}
}

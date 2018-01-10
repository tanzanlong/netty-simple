package com.tan.netty.usecase;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


public class ServerHandler extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new TimerServerHandler());
	}

}

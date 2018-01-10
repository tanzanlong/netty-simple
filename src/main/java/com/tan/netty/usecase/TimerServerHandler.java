package com.tan.netty.usecase;


import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class TimerServerHandler extends ChannelHandlerAdapter{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf=(ByteBuf) msg;
		byte[] b=new byte[buf.readableBytes()];
		buf.readBytes(b);
		String body=new String(b);
		System.out.println("receive from client order:"+body);
		String currenttime="query timer order".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"bad order";
		ByteBuf resp=Unpooled.copiedBuffer(currenttime.getBytes());
		ctx.write(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

}

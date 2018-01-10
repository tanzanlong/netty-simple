package com.tan.netty.usecase;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter{
	
	final ByteBuf msg;
	
	public ClientHandler(){
		String msgstr="query timer order";
		//msg=Unpooled.copiedBuffer(msgstr.getBytes());
		msg=Unpooled.buffer(msgstr.getBytes().length);
		msg.writeBytes(msgstr.getBytes());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//   ByteBuf message = null;  
//	        for (int i = 0; i < 100; i++) {  
//	            message = Unpooled.buffer(req.length);  
//	            message.writeBytes(req);  
//	            ctx.writeAndFlush(message);  
//	        }  
	    //    message.writeBytes(msg);  
	        ctx.writeAndFlush(msg);  
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf=(ByteBuf) msg;
		byte[] b=new byte[buf.readableBytes()];
		buf.readBytes(b);
		String resp=new String(b);
		System.out.println(" current time is :"+resp);
	}

}

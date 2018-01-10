package com.tan.netty.usecase.tcppackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientTcpPackageHandler extends ChannelHandlerAdapter{
	
	//final ByteBuf msg;
	byte msg[];
	
	int counter;
	
	public ClientTcpPackageHandler(){
		String msgstr="query timer order"+System.getProperty("line.separator");
		msg=msgstr.getBytes();
		//msg=Unpooled.copiedBuffer(msgstr.getBytes());
	
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
		for(int i=0; i<100;i++){
			ByteBuf message =Unpooled.buffer(msg.length);
			message.writeBytes(msg);
			 ctx.writeAndFlush(message);  
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		/*ByteBuf buf=(ByteBuf) msg;
		byte[] b=new byte[buf.readableBytes()];
		buf.readBytes(b);
		String resp=new String(b);*/
		String resp=(String) msg;
		System.out.println(" current time is :"+resp+"  counter:"+(++counter));
	}

}

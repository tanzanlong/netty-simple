package com.tan.netty.usecase.codecs.delimiter;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class EnchoServerHandler extends ChannelHandlerAdapter{
	
	private int counter;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
	//	ByteBuf buf=(ByteBuf) msg;
	//	byte[] b=new byte[buf.readableBytes()];
	//	buf.readBytes(b);
	//	String body=new String(b,"UTF-8");
		String body=(String) msg;
		//body=body.substring(0, b.length-System.getProperty("line.separator").length());
		System.out.println("receive from client order:"+body+" the counter:"+(++counter));
		body+="$_";
		ByteBuf resp=Unpooled.copiedBuffer(body.getBytes());
	//	ctx.write(resp);
		ctx.writeAndFlush(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

}

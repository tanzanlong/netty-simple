package com.tan.netty.api.transport.compare;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NettyNioServer {

    /**
     * 1.创建一个 ServerBootstrap
     * 2.使用 NioEventLoopGroup 允许非阻塞模式（NIO） 
     * 3.指定 ChannelInitializer 将给每个接受的连接调用 
     * 4.添加的ChannelInboundHandlerAdapter() 接收事件并进行处理 
     * 5.写信息到客户端，并添加 ChannelFutureListener 当一旦消息写入就关闭连接
     * 6.绑定服务器来接受连接 
     * 7.释放所有资源
     * 
     * @param port
     * @throws Exception
     */
    public void server(int port) throws Exception {
        final ByteBuf buf =
                Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n",
                        Charset.forName("UTF-8")));
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // 1
            b.group(bossGroup, workerGroup)
                    // 2
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 3
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() { // 4
                                                @Override
                                                public void channelActive(ChannelHandlerContext ctx)
                                                        throws Exception {
                                                    ctx.writeAndFlush(buf.duplicate()) // 5
                                                            .addListener(
                                                                    ChannelFutureListener.CLOSE);
                                                }
                                            });
                                }
                            });
            ChannelFuture f = b.bind().sync(); // 6
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync(); // 7
            workerGroup.shutdownGracefully().sync();
        }
    }

}

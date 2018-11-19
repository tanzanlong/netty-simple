package com.tan.netty.api.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class TransportApiServer {
    
    /**
     * 1.创建一个 ServerBootstrap 
     * 2.使用 OioEventLoopGroup 允许阻塞模式 
     * 3.指定 ChannelInitializer 将给每个接受的连接调用
     * 4.添加的 ChannelHandler 拦截事件，并允许他们作出反应 
     * 5.写信息到客户端，并添加 ChannelFutureListener 当一旦消息写入就关闭连接
     * 6.绑定服务器来接受连接 
     * 7.释放所有资源
     * 
     * @param port
     * @throws Exception
     */
    public void server(int port) throws Exception {
      /*  final ByteBuf buf =
                Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n",
                        Charset.forName("UTF-8")));*/
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); 

            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * 1.创建 ByteBuf 保存写的数据 2.写数据，并刷新 3.添加 ChannelFutureListener即可写操作完成后收到通知，
                             * 4.写操作没有错误完成 5.写操作完成时出现错误
                             */
                            ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8); // 1
                            ChannelFuture cf = ch.writeAndFlush(buf); // 2

                            cf.addListener(new ChannelFutureListener() { // 3
                                @Override
                                public void operationComplete(ChannelFuture future) {
                                    if (future.isSuccess()) { // 4
                                        System.out.println("Write successful");
                                    } else {
                                        System.err.println("Write error"); // 5
                                        future.cause().printStackTrace();
                                    }
                                }
                            });


                        }
                    });
            ChannelFuture f = b.bind().sync(); // 6
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync(); // 7
        }
    }
    
    public static void main(String[] args) throws Exception {
        TransportApiServer transportApiServer=new TransportApiServer();
        transportApiServer.server(8080);
    }

}

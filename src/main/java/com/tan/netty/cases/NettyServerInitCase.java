package com.tan.netty.cases;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerInitCase {
    private int port;

    public NettyServerInitCase(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        /*
         * ‘boss’，用来接收进来的连接。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        /**
         * ‘worker’，用来处理已经被接收的连接
         */
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) 
             .childHandler(new ChannelInitializer<SocketChannel>() { 
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new LogServerHandler());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)         
             .childOption(ChannelOption.SO_KEEPALIVE, true); 

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync(); 

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new NettyServerInitCase(port).run();
    }
}

package com.dpp.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @ClassName GroupChatServer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/09 10:40:00
 */
public class GroupChatServer {
    private int port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    //编写run方法，处理客户端请求
    public void run(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,123)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                //加入String解码器
                                .addLast("decoder",new StringDecoder())
                                //加入自己的业务处理handler
                                .addLast(new GroupChatServerHandler())
                                //加入String编码器
                                .addLast("encoder",new StringEncoder());
                    }
                });
        System.out.println("netty 服务器启动!");
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new GroupChatServer(8080).run();
    }
}

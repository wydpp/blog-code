package com.dpp.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName HeartbeatServer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 有心跳监测的服务端
 * @CreateTime 2022/11/09 14:45:00
 */
public class HeartbeatServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /*
                            加入一个netty提供的IdleStateHandler
                            long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit
                            1.IdleStateHandler是Netty提供的处理空闲状态channel的处理器
                            2.readerIdleTime：多长时间没有读，就会发送一个心跳检测包，检测是否连接
                            3.writerIdleTime：多长时间没有写，就会发送一个心跳检测包，检测是否连接
                            4.allIdleTime：多长时间没有写和读，就会发送一个心跳检测包，检测是否连接
                            5.当IdleStateEvent触发后，就会传递给管道的下一个handler处理。通过回调下一个handler的userEventTriggered,
                            通过该方法处理IdleStateEvent
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            pipeline.addLast(new HeartbeatServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

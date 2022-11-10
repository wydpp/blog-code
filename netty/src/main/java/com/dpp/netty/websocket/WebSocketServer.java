package com.dpp.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ClassName WebsocketServer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/10 15:14:00
 */
public class WebSocketServer {

    private int port;

    public WebSocketServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 123)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //因为基于http协议，所以要使用http编码和转码器
                        pipeline.addLast(new HttpServerCodec());
                        //是以块方式写，添加ChunkedWriteHandler处理器
                        pipeline.addLast(new ChunkedWriteHandler());
                        /*
                        1.http数据再传输过程中是分段的，HttpObjectAggregator可以将多个段聚合
                        2.这就是为什么当浏览器发送大量数据时，就会发出多次http请求。
                         */
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        /*
                        1.对应webSocket，它的数据是以帧（frame）形式传递
                        2.可以看到WebSocketFrame,下面有六个子类
                        3.浏览器请求uri：ws://localhost:7000/hello
                        4.WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议，保持长连接
                         */
                        pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                        //业务handler
                        pipeline.addLast(new WebSocketFrameServerHandler());
                    }
                });
        System.out.println("webSocket服务器启动...");
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new WebSocketServer(7000).run();
    }
}

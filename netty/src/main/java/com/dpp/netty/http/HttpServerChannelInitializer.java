package com.dpp.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName HttpServerChannelInitializer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/07 15:54:00
 */
public class HttpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个netty提供的httpServerCodec  codec=>coder-decoder
        //1.HttpServerCodec时netty提供的一个httpServer编码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2.增加一个自定义的处理器
        pipeline.addLast("MyHttpServerHandler", new HttpServerHandler());
    }
}

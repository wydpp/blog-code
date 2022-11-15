package com.dpp.netty.inboundhandleroutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @ClassName MyClientInitializer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/14 15:10:00
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个出战的handler，对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder());
        //入栈的解码器
        pipeline.addLast(new MyByteToLongDecoder());
        //加入一个自定义的handler，处理业务
        pipeline.addLast(new MyClientHandler());

    }
}

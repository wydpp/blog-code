package com.dpp.netty.inboundhandleroutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName MyClientHandler.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/14 15:15:00
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器的ip=" + ctx.channel().remoteAddress());
        System.out.println("收到服务器的数据=" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送数据
        System.out.println("MyClientHandler 发送数据 123456");
        ctx.writeAndFlush(123456L);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabdsdbabaabfabdsdd".getBytes(StandardCharsets.UTF_8)));
    }
}

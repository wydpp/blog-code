package com.dpp.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDate;

/**
 * @ClassName WebSocketFrameServerHandler.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description TextWebSocketFrame表示一个文本帧
 * @CreateTime 2022/11/10 15:26:00
 */
public class WebSocketFrameServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * web客户端连接后触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded被调用" + ctx.channel().id().asLongText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器端收到消息: " + msg.text());
        //回复消息
        ctx.writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDate.now() + ":" + msg.text()));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生" + cause.getMessage());
        ctx.close();
    }
}

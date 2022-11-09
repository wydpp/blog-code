package com.dpp.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName HttpServerHandler.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 1.SimpleChannelInboundHandler时ChannelInboundHandlerAdapter的子类
 * 2.HttpObject 客户但和服务端相互通讯的数据被封装成HttpObject
 * @CreateTime 2022/11/07 15:53:00
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg是不是httprequest请求
        if (msg instanceof HttpRequest) {
            System.out.println("msg类型= " + msg.getClass());
            System.out.println("客户端地址 " + ctx.channel().remoteAddress());
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了 favicon.ico,不响应.");
                return;
            }
            //回复信息给浏览器【http协议】
            ByteBuf content = Unpooled.copiedBuffer("Hello 我是服务器", StandardCharsets.UTF_8);
            //构造一个http的响应，httpResponse
            DefaultHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //将构建好的response返回
            ctx.writeAndFlush(httpResponse);
        }
    }
}

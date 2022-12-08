package com.dpp.netty.dubborpc.netty;

import com.dpp.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @ClassName NettyServerHandler.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/12/08 14:32:00
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息,并调用服务
        System.out.println("channelRead msg=" + msg);
        //客户端在调用服务端的api时，我们需要定义一个协议
        //比如我们要求每次发消息时，都必须以某个字符串开头："HelloService#hello#"
        String m = msg.toString();
        if (m.startsWith("HelloService#hello#")) {
            String response = new HelloServiceImpl().hello(m.substring(m.lastIndexOf("#") + 1));
            ctx.writeAndFlush(response);
        }
    }
}

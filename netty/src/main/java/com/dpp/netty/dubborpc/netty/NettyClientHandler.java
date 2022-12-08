package com.dpp.netty.dubborpc.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

/**
 * @ClassName NettyClientHandler.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/12/08 14:50:00
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    /**
     * 上下文
     */
    private ChannelHandlerContext context;
    /**
     * 返回值
     */
    private String response;
    /**
     * 客户端传入的参数
     */
    private String param;

    private Object object = new Object();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive event");
        context = ctx;
    }

    /**
     * 收到服务器的数据后调用
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = msg.toString();
        //唤醒等待的线程
        synchronized (object){
            object.notify();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 被代理对象调用，发送数据给服务器，wait->等待被唤醒->返回结果
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object call() throws Exception {
        context.writeAndFlush(param);
        //等待channelRead获取到结果后，唤醒
        synchronized (object){
            object.wait();
        }
        return response;
    }

    public void setParam(String param) {
        this.param = param;
    }
}

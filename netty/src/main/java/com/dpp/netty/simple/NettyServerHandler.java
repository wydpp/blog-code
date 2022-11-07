package com.dpp.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName NettyServerHandler.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 自定义一个handler，需要继承netty规定好的某个handlerAdapter
 * @CreateTime 2022/11/03 16:03:00
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据事件，这里我们可以读取客户端发送的消息
     * 1.ChannelHandlerContext ctx:上下文对象，含有pipeline，通道channel，连接地址等
     * 2.Object msg:客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        System.out.println("server ctx=" + ctx);
        //将msg转成ByteBuf
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是:" + buf.toString(StandardCharsets.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());

        //如果我们有一个非常耗时的业务->异步执行->提交该channel对应的NioEventLoop中的taskQueue中
        //解决方案1 用户程序自定义的普通任务
        ctx.channel().eventLoop().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端,异步执行的", StandardCharsets.UTF_8));
        });
        //解决方案2 用户自定义的定时任务,提交任务到ScheduledTaskQueue中
        ctx.channel().eventLoop().schedule(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端,异步定时任务", StandardCharsets.UTF_8));
        }, 5, TimeUnit.SECONDS);
    }

    /**
     * 数据读取完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓冲，并刷新
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", StandardCharsets.UTF_8));
    }

    /**
     * 处理异常，关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

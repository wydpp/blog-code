package com.dpp.netty.protobuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

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
        MyDataInfo.MyMessage myMessage = (MyDataInfo.MyMessage) msg;
        if (myMessage.getDateType() == MyDataInfo.MyMessage.DateType.StudentType) {
            System.out.println("客户端发送的数据student id=" + myMessage.getStudent().getId() + ",name=" + myMessage.getStudent().getName());
        } else {
            System.out.println("客户端发送的数据worker name=" + myMessage.getWorker().getName() + ",age=" + myMessage.getWorker().getAge());
        }
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

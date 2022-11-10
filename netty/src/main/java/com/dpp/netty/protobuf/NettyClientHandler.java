package com.dpp.netty.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @ClassName NettyClientHandler.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/03 16:24:00
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当通道就绪时触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //随机发送一个Student或Worker对象给服务器
        int random = new Random().nextInt(2);
        MyDataInfo.MyMessage myMessage = null;
        if (random == 0){
            myMessage = MyDataInfo.MyMessage.newBuilder().setDateType(MyDataInfo.MyMessage.DateType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("Tom")).build();
        }else {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDateType(MyDataInfo.MyMessage.DateType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(35).setName("Tom")).build();
        }
        ctx.writeAndFlush(myMessage);
    }

    /**
     * 当通道有读取事件时触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复消息：" + buf.toString(StandardCharsets.UTF_8));
        System.out.println("服务器的地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

package com.dpp.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName GroupChatServerHandler.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/09 10:48:00
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义几个channel集合，管理所有channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 连接建立时触发的事件，将当前channel加入到channelGroup中
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        /**
         * 将该客户加入聊天的信息推送给其他在线的客户端
         * channelGroup的writeAndFlush方法会遍历所有channel，并发送消息
         */
        channelGroup.writeAndFlush("【客户端】" + channel.remoteAddress() + "加入聊天\n");
        channelGroup.add(channel);
    }

    /**
     * 表示channel处理活动状态，提示xxx上线了
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(sdf.format(new Date()) + " " + channel.remoteAddress() + " 上线了~");
    }

    /**
     * 表示channel处理不活动状态，提示xxx离线了
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(sdf.format(new Date()) + " " + ctx.channel().remoteAddress() + " 离线了~");
    }

    /**
     * 断开连接，将xx客户端离开信息推送给当前在线客户
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(sdf.format(new Date()) + " " + "【客户端】" + channel.remoteAddress() + " 离开了\n");
        System.out.println("channelGroup size = " + channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        if (msg.startsWith("userName")) {
            //获取用户名称
            String userName = msg.split(":")[1];
            channel.attr(AttributeKey.valueOf("userName")).set(userName);
            return;
        }
        //遍历channelGroup，根据不同的情况，回送不同消息
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush(sdf.format(new Date()) + " " + "【客户】" + channel.attr(AttributeKey.valueOf("userName")).get() + " 说：" + msg + "\n");
            } else {
                ch.writeAndFlush(sdf.format(new Date()) + " " + "【自己】说：" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

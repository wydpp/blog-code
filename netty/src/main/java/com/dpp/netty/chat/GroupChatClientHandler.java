package com.dpp.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName GroupChatClientHandler.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/09 11:25:00
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {

    private String userName;

    public GroupChatClientHandler(String userName) {
        this.userName = userName;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}

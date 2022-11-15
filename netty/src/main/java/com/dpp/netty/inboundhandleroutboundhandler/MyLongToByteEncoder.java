package com.dpp.netty.inboundhandleroutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName MyLongToByteEncoder.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/14 15:12:00
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    /**
     * 编码方法
     *
     * @param channelHandlerContext
     * @param aLong
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        System.out.println("MyLongToByteEncoder encode 被调用");
        System.out.println("msg=" + aLong);
        byteBuf.writeLong(aLong);
    }
}

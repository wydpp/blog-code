package com.dpp.netty.inboundhandleroutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName MyByteToLongDecoder.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/14 15:01:00
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * decode会根据接收的数据，调用多次，直到确定没有新的元素被添加到list，或者bytebuf没有更多的可读字节为止
     * 如果list不为空，就会将list的内容传递给下一个chennelinboundhandler处理，该处理器的方法也会被调用多次
     * @param channelHandlerContext 上下文
     * @param byteBuf 入栈的ByteBuf
     * @param list LIst集合，解码后的数据放入里面
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyByteToLongDecoder 被调用");
        //long是8个字节
        if (byteBuf.readableBytes() >=8){
            list.add(byteBuf.readLong());
        }
    }
}

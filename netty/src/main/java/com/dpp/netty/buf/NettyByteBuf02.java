package com.dpp.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName NettyByteBuf02.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/08 17:53:00
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!哈哈", StandardCharsets.UTF_8);
        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            System.out.println(new String(content, 0, byteBuf.readableBytes(), StandardCharsets.UTF_8));
            System.out.println("ByteBuf=" + byteBuf);
            System.out.println(byteBuf.arrayOffset());//0
            System.out.println(byteBuf.readerIndex());//0
            System.out.println(byteBuf.writerIndex());//12
            System.out.println(byteBuf.readableBytes());//可读取的字节数，12
            System.out.println(byteBuf.capacity());//可读取的字节数，12
            for (int i=0;i<byteBuf.readableBytes();i++){
                System.out.println((char) byteBuf.getByte(i));
            }
            //区间读取，从index=1开始读取2个char=》此处输出：el
            System.out.println(byteBuf.getCharSequence(1,2,StandardCharsets.UTF_8));
        }
    }
}

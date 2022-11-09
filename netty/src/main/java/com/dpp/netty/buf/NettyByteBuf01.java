package com.dpp.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName NettyByteBuf01.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/08 17:19:00
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {
        /**
         * 创建一个ByteBuf
         * 1.创建一个对象，该对象包含一个数据arr=byte[10]
         * 2.再Netty的ByteBuf中，不需要使用flip进行反转，底层维护了readerIndex和writerIndex
         * 3.通过readerIndex和writerIndex和capacity将buf分成三个区域
         * 0-readerIndex->已经读取的区域
         * readerIndex=writerIndex->可读取的区域
         * writerIndex-capacity->可写的区域
         */
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.writeByte(i);
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }

    }
}

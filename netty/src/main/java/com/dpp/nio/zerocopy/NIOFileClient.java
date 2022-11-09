package com.dpp.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NIOFileClient.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/02 15:29:00
 */
public class NIOFileClient {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7001);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(address);
        String fileName = "./netty/src/main/resources/file/图片文件.png";
        FileInputStream fileInputStream = new FileInputStream(fileName);
        //得到fileChannel
        FileChannel fileChannel = fileInputStream.getChannel();
        //准备发送文件
        long start = System.currentTimeMillis();
        //在linux下一个transferTo方法就可以完成传输
        //在windows下一次调用transferTo只能发送8M，jiuxuyao分段传输文件，而且要注意传输时的位置
        //transferTo底层使用到零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("客户端发送的总字节数= " + transferCount + ",耗时：" + (System.currentTimeMillis() - start));
        fileChannel.close();
    }
}

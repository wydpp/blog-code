package com.dpp.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NIOFileClient.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 使用mmap测试
 * @CreateTime 2022/11/02 15:29:00
 */
public class NIOMMapFileClient {

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
        ByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY,0,fileChannel.size());
        long transferCount = socketChannel.write(byteBuffer);
        System.out.println("客户端发送的总字节数= " + transferCount + ",耗时：" + (System.currentTimeMillis() - start));
        fileChannel.close();
    }
}

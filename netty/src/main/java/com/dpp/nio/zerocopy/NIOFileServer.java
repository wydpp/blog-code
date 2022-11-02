package com.dpp.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NIOFileServer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description NIO文件服务端
 * @CreateTime 2022/11/02 15:21:00
 */
public class NIOFileServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7001);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount = 0;
            while (readCount != -1) {
                try {
                    readCount = socketChannel.read(byteBuffer);
                    System.out.println("读取到字节数readCount=" + readCount);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                byteBuffer.rewind();
            }
        }
    }
}

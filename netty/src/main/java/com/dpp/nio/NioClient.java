package com.dpp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName NioClient.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/10/28 14:42:00
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        //1.得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1",8080);
        boolean connectSuccess = socketChannel.connect(socketAddress);
        if (!connectSuccess){
            while (!socketChannel.finishConnect()){
                System.out.println("连接需要时间，客户端不会阻塞!");
            }
        }
        //连接成功，发送数据
        String msg = "hello 霸都老段!";
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        //写入数据
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}

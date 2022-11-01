package com.dpp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

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
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8080);
        boolean connectSuccess = socketChannel.connect(socketAddress);
        while (!connectSuccess) {
            connectSuccess = socketChannel.finishConnect();
        }
        String msg = "hello 霸都老段!";
        write(socketChannel,msg);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        while (true) {
            int keys = selector.select();
            if (keys > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isReadable()) {
                        SocketChannel sc = (SocketChannel) selectionKey.channel();
                        System.out.println("客户端读取服务端返回数据:" + read(sc));
                    }
                }
            }
        }
    }

    private static String read(SocketChannel sc) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        int read = sc.read(byteBuffer);
        return new String(byteBuffer.array(), 0, read, StandardCharsets.UTF_8.name());
    }

    private static String write(SocketChannel sc, String msg) throws IOException {
        System.out.println("客户端发送数据" + msg);
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        sc.write(byteBuffer);
        return msg;
    }
}

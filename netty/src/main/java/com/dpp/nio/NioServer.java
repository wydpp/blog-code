package com.dpp.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @ClassName NioServer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/09/27 16:20:00
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        System.out.println("初始化keys=" + selector.keys().size());
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("serverSocketChannel注册后keys=" + selector.keys().size());
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8080);
        serverSocket.bind(inetSocketAddress);
        System.out.println("服务已启动");
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel acceptSocketChannel = socketChannel.accept();
                    System.out.println("有一个客户端进来了!id=" + acceptSocketChannel.getRemoteAddress());
                    acceptSocketChannel.configureBlocking(false);
                    acceptSocketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                    System.out.println("socketChannel注册后keys=" + selector.keys().size());
                }
                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                    System.out.println("socket信息流开始读取");
                    int len = socketChannel.read(byteBuffer);
                    System.out.println("len=" + len);
                    while (len > 0) {
                        String msg = new String(byteBuffer.array(), 0, len, StandardCharsets.UTF_8.name());
                        System.out.println(msg);
                        len = socketChannel.read(byteBuffer);
                    }
                    if (len < 0) {
                        System.out.println("客户都断开连接");
                        socketChannel.close();
                    }
                    System.out.println("socket信息读取完毕!");
                    write(socketChannel,"我是服务器，收到你的消息了!");
                    write(socketChannel,"我是服务器，收到你的消息了2!");
                }
            }
        }
    }

    private static String write(SocketChannel sc, String msg) throws IOException {
        System.out.println("服务端发送数据" + msg);
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        sc.write(byteBuffer);
        return msg;
    }
}

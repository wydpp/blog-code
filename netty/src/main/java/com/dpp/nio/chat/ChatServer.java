package com.dpp.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @ClassName ChatServer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 聊天室服务端
 * @CreateTime 2022/11/01 15:14:00
 */
public class ChatServer {

    private Selector selector;

    private ServerSocketChannel listenChannel;

    private static final int PORT = 6666;

    public ChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.configureBlocking(false);
            listenChannel.bind(new InetSocketAddress(PORT));
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select(1000);
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            System.out.println(sc.getRemoteAddress() + " 上线");
                        }
                        if (selectionKey.isReadable()) {
                            read(selectionKey);
                        }
                    }
                } else {
                    System.out.println("等待客户端连接");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                listenChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取客户端消息
     *
     * @param selectionKey
     * @return
     * @throws IOException
     */
    private void read(SelectionKey selectionKey) {
        SocketChannel sc = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        int count = 0;
        try {
            count = sc.read(byteBuffer);
            if (count > 0) {
                String msg = new String(byteBuffer.array(), 0, count, StandardCharsets.UTF_8);
                System.out.println("form 客户端(" + sc.getRemoteAddress() + "):" + msg);
                //向其它客户打转发消息
                sendMsgToOtherClients(msg, sc);
            }
        } catch (IOException e) {
            try {
                System.out.println(sc.getRemoteAddress() + " 离线了..");
                //取消注册
                selectionKey.cancel();
                //关闭通道
                sc.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 转发消息给其他客户端
     *
     * @param msg
     * @param self
     */
    private void sendMsgToOtherClients(String msg, SocketChannel self) {
        System.out.println("服务器转发消息中...");
        selector.keys().forEach(key -> {
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                try {
                    ((SocketChannel) targetChannel).write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new ChatServer().listen();
    }
}

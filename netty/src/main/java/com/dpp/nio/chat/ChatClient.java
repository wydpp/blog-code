package com.dpp.nio.chat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ChatClient.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 聊天室客户端
 * @CreateTime 2022/11/01 15:43:00
 */
public class ChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6666;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public ChatClient() {
        try {
            this.selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            boolean success = socketChannel.connect(new InetSocketAddress(HOST, PORT));
            socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
            username = "客户端1";
            while (!success){
                success = socketChannel.finishConnect();
            }
            System.out.println(username + " is ok...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        msg = username + " 说：" + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg() {
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        SocketChannel sc = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int count = sc.read(byteBuffer);
                        String string = new String(byteBuffer.array(),0,count,StandardCharsets.UTF_8);
                        System.out.println(string);
                    }
                    iterator.remove();
                }
            }else {
                System.out.println("没有可用的通道...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //启动客户端
        ChatClient chatClient = new ChatClient();
        //读取数据
        new Thread(){
            @Override
            public void run() {
                while (true){
                    chatClient.readMsg();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            chatClient.sendMsg(s);
        }

    }

}

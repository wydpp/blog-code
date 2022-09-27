package com.dpp.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName NioServer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/09/27 16:06:00
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket();
        SocketAddress socketAddress = new InetSocketAddress(8080);
        serverSocket.bind(socketAddress);
        System.out.println("服务已启动");
        while(true){
            Socket socket = serverSocket.accept();
            System.out.println("有一个客户端进来了!");
            executorService.submit(()-> {
                try {
                    readSocket(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
    private static void readSocket(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        System.out.println("socket信息流开始读取");
        int len = inputStream.read(bytes);
        while (len > 0){
            String msg = new String(bytes,0,len);
            System.out.println(msg);
            len = inputStream.read(bytes);
        }
        System.out.println("socket信息读取完毕!");
    }
}

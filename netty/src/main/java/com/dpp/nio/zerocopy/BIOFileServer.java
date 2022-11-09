package com.dpp.nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName BIOFileServer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description BIO接收文件服务端
 * @CreateTime 2022/11/02 14:59:00
 */
public class BIOFileServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] byteArray = new byte[1024];
            int maxBytes = 0;
            try {
                while (true) {
                    int readCount = dataInputStream.read(byteArray);
                    if (readCount < 0) {
                        System.out.println("数据读取结束...字节数=" + maxBytes);
                        break;
                    }
                    maxBytes += readCount;
                    System.out.println("读取到数据...readCount=" + readCount);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("客户端断开，文件接收结束，接收字节数：" + maxBytes);
            } finally {
                dataInputStream.close();
                socket.close();
            }

        }
    }
}

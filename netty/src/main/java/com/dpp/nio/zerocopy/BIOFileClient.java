package com.dpp.nio.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @ClassName BIOSendFile.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description BIO发送文件客户端
 * @CreateTime 2022/11/02 14:58:00
 */
public class BIOFileClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 7001);
        String fileName = "./netty/src/main/resources/file/图片文件.png";
        FileInputStream fileInputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[1024];
        int readCount;
        int total = 0;
        long startTime = System.currentTimeMillis();
        while ((readCount = fileInputStream.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer, 0, readCount);
            System.out.println("发送字节数：" + readCount);
        }
        System.out.println("发送总字节数: " + total + ",耗时： " + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        fileInputStream.close();
    }
}

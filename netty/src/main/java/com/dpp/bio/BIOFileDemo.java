package com.dpp.bio;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName BIOFileDemo.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description BIO操作文件示例
 * @CreateTime 2022/10/17 13:28:00
 */
public class BIOFileDemo {

    public static void main(String[] args) throws Exception {
        readFileByInputStream();
        readFileByRandomAccessFile();
    }

    /**
     * InputStream读取文件
     */
    public static void readFileByInputStream() throws IOException {
        File file = new File("./netty/src/main/resources/file/1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (true) {
            //指定读取指针位置
            int read = fileInputStream.read(bytes);
            if (read < 0) {
                break;
            }
            sb.append(new String(bytes, 0, read, StandardCharsets.UTF_8));
        }
        fileInputStream.close();
        System.out.println("readFileByInputStream=>文件内容是：\n" + sb);
    }

    /**
     * RandomAccessFile读取文件示例
     *
     * @throws IOException
     */
    public static void readFileByRandomAccessFile() throws IOException {
        File file = new File("./netty/src/main/resources/file/1.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        long fileLength = file.length();
        byte[] bytes = new byte[10];
        StringBuilder sb = new StringBuilder();
        long position = 0;
        while (true) {
            //指定读取指针位置
            randomAccessFile.seek(position);
            int read = randomAccessFile.read(bytes);
            if (read < 0) {
                break;
            }
            position += read;
            if (position > fileLength){
                position = fileLength;
            }
            sb.append(new String(bytes, 0, read, StandardCharsets.UTF_8));
        }
        randomAccessFile.close();
        System.out.println("readFileByRandomAccessFile=>文件内容是：\n" + sb);
    }
}

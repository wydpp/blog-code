package com.dpp.nio;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

/**
 * @ClassName NIOFileDemo.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/10/17 14:52:00
 */
public class NIOFileDemo {

    public static void main(String[] args) throws Exception {
        readFileByFileChannel();
        readFileByMappedByteBuffer();
    }

    public static void readFileByFileChannel() throws IOException {
        File file = new File("./netty/src/main/resources/file/1.txt");
        FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        StringBuilder sb = new StringBuilder();
        while (true) {
            byteBuffer.clear();
            int read = fileChannel.read(byteBuffer);
            if (read < 0) {
                break;
            }
            sb.append(new String(byteBuffer.array(), 0, read, StandardCharsets.UTF_8));
        }
        fileChannel.close();
        System.out.println("readFileByFileChannel=>文件内容是：\n" + sb);
    }

    public static void readFileByMappedByteBuffer() throws IOException {
        File file = new File("./netty/src/main/resources/file/1.txt");
        FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ);
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
        int fileLength = (int) fileChannel.size();
        byte[] bytes = new byte[10];
        StringBuilder sb = new StringBuilder();
        int position = 0;
        while (true) {
            mappedByteBuffer.position(position);
            int size = Math.min(bytes.length, fileLength - position);
            mappedByteBuffer.get(bytes, 0, size);
            sb.append(new String(bytes, 0, size, StandardCharsets.UTF_8));
            position = position + bytes.length;
            if (position >= fileLength) {
                break;
            }
        }
        fileChannel.close();
        System.out.println("readFileByMappedByteBuffer=>文件内容是：\n" + sb);
    }
}

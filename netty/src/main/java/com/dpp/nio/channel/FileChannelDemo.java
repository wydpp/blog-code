package com.dpp.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName FileChannelDemo.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/10/12 15:35:00
 */
public class FileChannelDemo {
    public static void main(String[] args) throws IOException {
        //writeDemo();
        //readDemo();
        //readAndWriteByBuffer();
//        transferFromDemo();
        mappedByteBufferDemo();
    }

    /**
     * 把字符串写入到指定文件中
     *
     * @throws IOException
     */
    public static void writeDemo() throws IOException {
        String str = "hello dpp";
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./netty/src/main/resources/writeDemo.log");
        //通过fileOutputStream获取对应的FileChannel
        FileChannel channel = fileOutputStream.getChannel();
        //创建一个缓冲区Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //将 str放入byteBuffer
        buffer.put(str.getBytes(StandardCharsets.UTF_8));
        //对buffer反转
        buffer.flip();
        //把buffer数据写入FileChannel
        channel.write(buffer);
        fileOutputStream.close();
    }

    /**
     * 读取本地文件数据
     */
    public static void readDemo() throws IOException {
        //创建文件输入流对象
        FileInputStream fileInputStream = new FileInputStream("./netty/src/main/resources/writeDemo.log");
        //通多FileInputStream获取FileChannel
        FileChannel fileChannel = fileInputStream.getChannel();
        //创建一个缓冲区ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
        //把文件数据读取到缓冲区中
        fileChannel.read(byteBuffer);
        byteBuffer.flip();
        //将byteBuffer字节转成字符串
        System.out.println(new String(byteBuffer.array()));
    }

    /**
     * 使用一个Buffer完成文件读取：
     * 1.使用FileChannel完成文件拷贝
     * 2.拷贝一个文本文件1.txt,放在项目下2.txt
     */
    public static void readAndWriteByBuffer() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("./netty/src/main/resources/file/1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("./netty/src/main/resources/file/2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true){
            byteBuffer.clear();
            int read = fileChannel01.read(byteBuffer);
            System.out.println("read="+read);
            if (read == -1){
                break;
            }
            //将byteBuffer中的数据写入2.txt
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }

    }

    /**
     * 使用FileChannel的transferFrom方法，完成文件拷贝
     */
    public static void transferFromDemo() throws IOException {
        //1.创建一个文件读取流和一个文件输入流
        FileInputStream fileInputStream = new FileInputStream("./netty/src/main/resources/file/肥东.webp");
        FileOutputStream fileOutputStream = new FileOutputStream("./netty/src/main/resources/file/肥东副本.webp");
        //2.获取对应的FileChannel
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();
        //3.使用transferFrom完成拷贝
        outputStreamChannel.transferFrom(inputStreamChannel,0,inputStreamChannel.size());
        //4.关闭线管通道和流
        outputStreamChannel.close();
        inputStreamChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }

    /**
     * 堆外内存修改文件
     * @throws IOException
     */
    public static void mappedByteBufferDemo() throws IOException {
        /**
         * 1.MappedByteBuffer可以让文件直接再内存（堆外内存）中修改，操作系统不需要拷贝一次
         */
        RandomAccessFile randomAccessFile = new RandomAccessFile("./netty/src/main/resources/file/1.txt","rw");
        //2.获取对用通道
        FileChannel fileChannel = randomAccessFile.getChannel();
        /**
         * 1:FileChannel.MapMode.READ_WRITE 使用读写模式
         * 2:0   直接修改的起始位置
         * 3：5  是映射到内存的大小，即将1.txt的多少个字节映射到内存
         * 直接修改的范围是[0,5)
         */
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        //修改数据
        mappedByteBuffer.put(0, (byte) 'H');
        randomAccessFile.close();
        System.out.println("在文件目录中打开，发现已修改");
    }

}

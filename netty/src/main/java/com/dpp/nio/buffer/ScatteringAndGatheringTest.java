package com.dpp.nio.buffer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @ClassName ScatteringAndGatheringTest.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/10/13 10:34:00
 */
public class ScatteringAndGatheringTest {
    /**
     * Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入
     * Gathering:分散，从buffer读取数据时，可以buffer数组，依次读取
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        //使用ServerSocketChannel和SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8888);
        //绑定端口并启动
        serverSocketChannel.bind(inetSocketAddress);
        //创建一个buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        //等待客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();
        //假定读取8个字节
        int messageLength = 8;
        //循环读取
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                byteRead += socketChannel.read(byteBuffers);
                System.out.println("byteRead=" + byteRead);
                //输出流打印，看当前buffer的position和limit
                Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position=" + byteBuffer.position() + ",limit=" + byteBuffer.limit()).forEach(System.out::println);
            }
            //将所有的buffer进行flip
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                byteWrite += socketChannel.write(byteBuffers);
                System.out.println("byteWrite=" + byteWrite);
            }
            //将所有的buffer进行clear
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("byteRead=" + byteRead + " byteWrite=" + byteWrite + ", messageLength=" + messageLength);

        }

    }
}

package com.dpp.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @ClassName NettyServer.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/03 15:48:00
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //1.创建BossGroup和WorkerGroup
        //创建了两个线程组bossGroup和workerGroup
        //bossGroup只处理客户端连接，真正的客户端业务处理交给workerGroup完成
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //2.创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        //使用链式编程进行设置
        //设置线程组
        try {
            bootstrap.group(bossGroup, workerGroup)
                    //使用NioServerSocketChannel作为通道实现
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列等待连接个数。对应TCP/IP协议listen函数中的backlog参数，用来初始化服务器可连接队列大小。
                    //服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接。
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //给workerGroup的NioEventLoop对应的管道设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("客户端socketchannel hascode=" + socketChannel.hashCode());
                            socketChannel.pipeline()
                                    //加一个ProtoBufDecoder,需要指定对那种对象进行解码
                                    .addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()))
                                    .addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器 is ready...");
            //绑定端口,并同步，返回一个channelFuture对象。(服务器启动)
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        System.out.println("监听6666端口成功!");
                    }else {
                        System.out.println("监听6666端口失败");
                    }
                }
            });
            //对关闭通道进行监听
            //ChannelFuture sync():等待异步操作执行完毕
            //此处等待通道关闭执行完毕，如果通道没有关闭，会一直阻塞
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}

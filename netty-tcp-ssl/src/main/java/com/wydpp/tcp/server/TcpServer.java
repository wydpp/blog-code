package com.wydpp.tcp.server;

import com.wydpp.tcp.server.ssl.ContextSslFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLEngine;

/**
 * @author wydpp
 */
@Component
public class TcpServer implements CommandLineRunner {

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(10);
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel socketChannel) {
                        //添加编解码
                        socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                        socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                        socketChannel.pipeline().addLast(new TcpServerHandler());
                        SSLEngine engine = ContextSslFactory.getSslContext().createSSLEngine();
                        engine.setUseClientMode(false);
                        engine.setNeedClientAuth(true);
                        socketChannel.pipeline().addFirst(new SslHandler(engine));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1", 8888);
        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        TcpServer tcpServer = new TcpServer();
        tcpServer.start();
    }


    @Override
    public void run(String... args) throws Exception {
        start();
    }
}

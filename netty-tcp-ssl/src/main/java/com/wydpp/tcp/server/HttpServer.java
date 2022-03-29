package com.wydpp.tcp.server;

import com.wydpp.tcp.server.ssl.ContextSslFactory;
import com.wydpp.tcp.server.ssl.SslContextFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLEngine;

/**
 * @author wydpp
 */
@Component
public class HttpServer implements CommandLineRunner {

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(10);
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        //添加编解码
                        socketChannel.pipeline()
                                .addLast(buildSslHandler())
                                .addLast(new HttpRequestDecoder())
                                .addLast(new HttpObjectAggregator(65536))
                                .addLast(new HttpResponseEncoder())
                                .addLast(new HttpServerHandler())

                        ;
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

//    private SslHandler buildSslHandler(SocketChannel socketChannel) {
//        return SslContextFactory.getSslContext().newHandler(socketChannel.alloc());
//    }

    private SslHandler buildSslHandler() {
        SSLEngine sslEngine = ContextSslFactory.getSslContext().createSSLEngine();
        sslEngine.setUseClientMode(false);
        sslEngine.setNeedClientAuth(false);
        return new SslHandler(sslEngine);
    }


    @Override
    public void run(String... args) throws Exception {
        start();
    }
}

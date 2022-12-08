package com.dpp.netty.dubborpc.provider;

import com.dpp.netty.dubborpc.netty.NettyServer;

/**
 * @ClassName ServerBootstrap.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/12/08 14:25:00
 */
public class ServerBootstrap {

    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7000);
    }
}

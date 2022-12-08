package com.dpp.netty.dubborpc.provider;

import com.dpp.netty.dubborpc.publicinterface.IHelloService;

/**
 * @ClassName HelloServiceImpl.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/12/08 14:22:00
 */
public class HelloServiceImpl implements IHelloService {

    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息=" + msg);
        //根据msg返回不同的结果
        if (msg != null) {
            return "你好客户端，我已收到你的消息！【" + msg + "】";
        }
        return "你好客户端，我已收到你的消息！";
    }
}

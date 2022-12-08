package com.dpp.netty.dubborpc.customer;

import com.dpp.netty.dubborpc.netty.NettyClient;
import com.dpp.netty.dubborpc.publicinterface.IHelloService;

/**
 * @ClassName ClientBootstrap.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/12/08 15:16:00
 */
public class ClientBootstrap {
    /**
     * 协议头
     */
    public static final String PROVIDER_NAME = "HelloService#hello#";

    public static void main(String[] args) {
        //创建一个消费者
        NettyClient customer = new NettyClient();
        //创建代理对象
        IHelloService helloService = (IHelloService) customer.getBean(IHelloService.class, PROVIDER_NAME);
        String response = helloService.hello("你好 dubbo~");
        System.out.println("服务端返回的结果=" + response);
    }
}

package com.dpp.netty.dubborpc.publicinterface;

/**
 * @ClassName IHelloService.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 服务提供方和消费方公用的接口
 * @CreateTime 2022/12/08 14:21:00
 */
public interface IHelloService {

    String hello(String msg);
}

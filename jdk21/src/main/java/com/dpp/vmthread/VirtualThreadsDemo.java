package com.dpp.vmthread;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @ClassName VirtualThreadsDemo.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 虚拟线程示例
 * @CreateTime 2023/10/13 15:36:00
 */
public class VirtualThreadsDemo {

    private static void testCreateVirtualThread1() throws InterruptedException {
        Thread thread = Thread.ofVirtual().name("虚拟线程1").start(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " Hello this is virtual thread!");
            }
        });
        thread.join();
    }

    private static void testCreateVirtualThread2() throws Exception {
        Thread.Builder builder = Thread.ofVirtual().name("虚拟线程2");
        Runnable task = () -> {
            System.out.println("Running thread");
        };
        Thread t = builder.start(task);
        System.out.println(t.toString());
        t.join();
    }

    /**
     * 可以在idea中配置环境变量"vm options"：
     * -Djdk.virtualThreadScheduler.parallelism=1
     * -Djdk.virtualThreadScheduler.maxPoolSize=1
     * -Djdk.virtualThreadScheduler.minRunnable=1
     * @throws Exception
     */
    private static void testCreateVirtualThread3() throws Exception {
        final ThreadFactory factory = Thread.ofVirtual().name("routine-", 0).factory();
        try (var executor = Executors.newThreadPerTaskExecutor(factory)) {
            var t1 = executor.submit(new Runnable() {
                @Override
                public void run() {
                    log("jerry上课了");
                    try {
                        Thread.sleep(Duration.ofSeconds(1));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log("jerry下课了");
                }
            });
            var t2 = executor.submit(new Runnable() {
                @Override
                public void run() {
                    log("sea上课了");
                    try {
                        Thread.sleep(Duration.ofSeconds(1));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log("sea下课了");
                }
            });
            t1.get();
            t2.get();
        }
    }

    /**
     * 在idea中配置环境变量"vm options"：
     * -Djdk.virtualThreadScheduler.parallelism=1
     * -Djdk.virtualThreadScheduler.maxPoolSize=1
     * -Djdk.virtualThreadScheduler.minRunnable=1
     * @throws Exception
     */
    private static void testHttpClientInVirtualThread() throws Exception {
        final ThreadFactory factory = Thread.ofVirtual().name("routine-", 0).factory();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        ClassicHttpRequest httpGet = ClassicRequestBuilder.get("http://127.0.0.1:8080/health").build();
        Runnable jerry = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log("jerry http request start");
                httpclient.execute(httpGet);
                log("jerry http request end");
            }
        };

        Runnable sea = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log("sea http request start");
                httpclient.execute(httpGet);
                log("sea http request end");
            }
        };
        //虚拟线程-单线程测试
        try (var executor = Executors.newThreadPerTaskExecutor(factory)) {
            var t1 = executor.submit(jerry);
            var t2 = executor.submit(sea);
            t1.get();
            t2.get();
            //http请求会并发执行
        }
    }
    private static void log(String message) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(LocalDateTime.now().format(dateTimeFormatter) + ":" + message + " | " + Thread.currentThread());
    }


    public static void main(String[] args) throws Exception {
        //testCreateVirtualThread1();
        testHttpClientInVirtualThread();
    }


}

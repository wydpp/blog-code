package com.dpp.deferred.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/query")
public class DeferredController {

    private final Logger logger = LoggerFactory.getLogger(DeferredController.class);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    @GetMapping("/common/{index}")
    public String commonQuery(@PathVariable("index") String index, HttpServletRequest request) throws InterruptedException {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            logger.info("请求头name={},value={}", name, request.getHeader(name));
        }
        logger.info(index + " common开始!");
        logger.info(index + " common读取文件开始!");
        //TimeUnit.SECONDS.sleep(5);
        logger.info(index + " common读取文件结束!");
        logger.info(index + " common结束!");
        return index + " common";
    }

    @GetMapping("/async-tomcat/{index}")
    public void callableQuery(HttpServletRequest request, @PathVariable("index") String index) {
        logger.info(index + " async-tomcat start");
        AsyncContext asyncContext = request.startAsync();
        new Thread(() -> {
            logger.info(index + " callable读取文件开始!");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info(index + " callable读取文件结束");
            //处理响应内容
            try {
                asyncContext.getResponse().getWriter()
                        .write(index + " async-tomcat");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //异步处理完成
            asyncContext.complete();
        }).start();
        logger.info(index + " async-tomcat 结束!");
    }

    @GetMapping("/async-deferred/{index}")
    public DeferredResult<String> deferredQuery(@PathVariable("index") String index) {
        logger.info(index + " deferred开始!");
        //泛型类型表示返回的对象类型
        DeferredResult<String> deferredResult = new DeferredResult<>();
        executorService.execute(() -> {
            logger.info(index + " deferred读取文件开始!");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //写入响应内容
            deferredResult.setResult(index + " deferred");
            logger.info(index + " deferred读取文件结束");
        });
        logger.info(index + " deferred结束!");
        return deferredResult;
    }

}

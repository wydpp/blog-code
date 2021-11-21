package com.dpp.deferred.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeUnit;

@RestController
public class DeferredController {

    private final Logger logger = LoggerFactory.getLogger(DeferredController.class);


    @GetMapping("/jerry")
    public String queryJerry() throws InterruptedException {
        logger.info("开始查询 jerry!");
        logger.info("开始读取文件 !");
        TimeUnit.SECONDS.sleep(2);
        logger.info("查询结束 jerry!");
        return "Jerry";
    }

    @GetMapping("/mark")
    public DeferredResult<String> queryMark() throws InterruptedException {
        logger.info("开始查询 mark 信息!");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        TimeUnit.SECONDS.sleep(2);
        new Thread(()->{
            logger.info("开始读取文件 !");
            deferredResult.setResult("mark");
        }).start();
        logger.info("查询 mark 结束!");
        return deferredResult;
    }
}

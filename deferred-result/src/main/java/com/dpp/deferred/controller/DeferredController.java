package com.dpp.deferred.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/query")
public class DeferredController {

    private final Logger logger = LoggerFactory.getLogger(DeferredController.class);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    @GetMapping("/common")
    public String commonQuery() throws InterruptedException {
        logger.info("common开始!");
        logger.info("common读取文件开始!");
        TimeUnit.SECONDS.sleep(2);
        logger.info("common读取文件结束!");
        logger.info("common结束!");
        return "common";
    }

    @GetMapping("/callable")
    public Callable<String> callableQuery() {
        logger.info("callable开始!");
        Callable<String> callable = () -> {
            logger.info("callable读取文件开始!");
            TimeUnit.SECONDS.sleep(2);
            logger.info("callable读取文件结束!");
            return "callable";
        };
        logger.info("callable 结束!");
        return callable;
    }

    @GetMapping("/deferred")
    public DeferredResult<String> deferredQuery() throws InterruptedException {
        logger.info("deferred开始!");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        TimeUnit.SECONDS.sleep(2);
        executorService.execute(() -> {
            logger.info("deferred读取文件开始!");
            deferredResult.setResult("deferred");
            logger.info("deferred读取文件结束");
        });
        logger.info("deferred结束!");
        return deferredResult;
    }
}

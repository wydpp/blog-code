package com.dpp.deferred.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    @GetMapping("/common/{index}")
    public String commonQuery(@PathVariable("index") String index) throws InterruptedException {
        logger.info(index + " common开始!");
        logger.info(index + " common读取文件开始!");
        TimeUnit.SECONDS.sleep(5);
        logger.info(index + " common读取文件结束!");
        logger.info(index + " common结束!");
        return index + " common";
    }

    @GetMapping("/callable/{index}")
    public Callable<String> callableQuery(@PathVariable("index") String index) {
        logger.info(index + " callable开始!");
        Callable<String> callable = () -> {
            logger.info(index + " callable读取文件开始!");
            TimeUnit.SECONDS.sleep(5);
            logger.info(index + " callable读取文件结束!");
            return index + " callable";
        };
        logger.info(index + " callable 结束!");
        return callable;
    }

    @GetMapping("/deferred/{index}")
    public DeferredResult<String> deferredQuery(@PathVariable("index") String index) throws InterruptedException {
        logger.info("deferred开始!");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        executorService.execute(() -> {
            logger.info(index + " deferred读取文件开始!");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deferredResult.setResult(index + " deferred");
            logger.info(index + " deferred读取文件结束");
        });
        logger.info(index + " deferred结束!");
        return deferredResult;
    }
}

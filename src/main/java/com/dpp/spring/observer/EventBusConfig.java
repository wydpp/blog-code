package com.dpp.spring.observer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class EventBusConfig {

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster =
                new SimpleApplicationEventMulticaster();
        //添加Executor,设置线程名前缀
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setThreadNamePrefix("applicationListener_");
        eventMulticaster.setTaskExecutor(asyncTaskExecutor);
        //添加异常处理
        eventMulticaster.setErrorHandler(t -> {
            System.out.println("listener异常处理程序");
            t.printStackTrace();
        });
        return eventMulticaster;
    }
}

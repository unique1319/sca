package com.wrh.spring.thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/14 15:19
 * @describe
 */

@EnableAsync
@SpringBootApplication
public class ThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadApplication.class, args);
    }

    /**
     * 提交任务：
     * 1、无返回值的任务使用execute(Runnable)
     * 2、有返回值的任务使用submit(Runnable)
     *
     * 处理流程:
     * 1、当一个任务被提交到线程池时，首先查看线程池的核心线程是否都在执行任务，否就选择一条线程执行任务，是就执行第二步。
     * 2、查看核心线程池是否已满，不满就创建一条线程执行任务，否则执行第三步。
     * 3、查看任务队列是否已满，不满就将任务存储在任务队列中，否则执行第四步。
     * 4、查看线程池是否已满，不满就创建一条线程执行任务，否则就按照策略处理无法执行的任务。
     */
    @Bean
    public ThreadPoolTaskExecutor threadPool() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(3);
        threadPoolTaskExecutor.setMaxPoolSize(15);
        threadPoolTaskExecutor.setQueueCapacity(3);
        threadPoolTaskExecutor.setKeepAliveSeconds(1000);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setThreadNamePrefix("custom-thread-");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }


}

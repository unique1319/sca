package com.wrh.spring.thread.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/14 15:37
 * @describe
 */

@Slf4j
@Service
public class ExecuteService {

    @Async("threadPool")
    public void execute() {
        log.info("start executeAsync");
        try {
            System.out.println("当前运行的线程名称：" + Thread.currentThread().getName());
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("end executeAsync");
    }

}

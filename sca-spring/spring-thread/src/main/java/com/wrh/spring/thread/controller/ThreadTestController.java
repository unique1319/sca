package com.wrh.spring.thread.controller;

import com.wrh.spring.thread.service.ExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/14 15:40
 * @describe
 */

@RestController
public class ThreadTestController {

    @Autowired
    ExecuteService executeService;

    @RequestMapping("/thread")
    public String thread() {
        executeService.execute();
        return "finish";
    }


}

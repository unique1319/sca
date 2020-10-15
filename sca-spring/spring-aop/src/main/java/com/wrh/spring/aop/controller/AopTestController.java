package com.wrh.spring.aop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/14 15:40
 * @describe
 */

@RestController
public class AopTestController {

    @RequestMapping("/aop")
    public Object aop() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                list.add(999);
            }
        }
        return list;
    }


}

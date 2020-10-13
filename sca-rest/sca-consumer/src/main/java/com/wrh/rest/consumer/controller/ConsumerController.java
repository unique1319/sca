package com.wrh.rest.consumer.controller;

import com.wrh.rest.consumer.feign.ConsumerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/12 17:26
 * @describe
 */

@RestController
public class ConsumerController {

    private final ConsumerFeign consumerFeign;

    @Autowired
    public ConsumerController(ConsumerFeign consumerFeign) {
        this.consumerFeign = consumerFeign;
    }

    @GetMapping("/getProvider")
    public String getProvider() {
        return "消费者调用生产者：" + consumerFeign.provider();
    }

}

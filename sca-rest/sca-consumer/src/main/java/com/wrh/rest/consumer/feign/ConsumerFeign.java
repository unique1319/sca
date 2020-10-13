package com.wrh.rest.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/13 9:21
 * @describe
 */

@FeignClient(name = "sca-provider", fallbackFactory = FeignServiceFeign.class)
public interface ConsumerFeign {

    /**
     * 路径、方法名称，必须与生产者一样，否则无法调用
     */
    @GetMapping("/provider")
    String provider();

}

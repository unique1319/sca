package com.wrh.rest.consumer.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/13 9:23
 * @describe
 */
@Component
public class FeignServiceFeign implements FallbackFactory<ConsumerFeign> {

    @Override
    public ConsumerFeign create(Throwable throwable) {
        return new ConsumerFeign() {
            @Override
            public String provider() {
                return "生产者sca-provider服务被降级停用了";
            }
        };
    }
}

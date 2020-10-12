package com.wrh.rest.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ScaRestProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScaRestProviderApplication.class, args);
    }

}

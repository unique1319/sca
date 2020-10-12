package com.wrh.rest.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/12 17:26
 * @describe
 */

@RestController
public class ProviderController {

    @GetMapping("/provider")
    public String provider() {
        return "Hello Provider";
    }

}

package com.ping.petservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：Ping
 * @Date：2023/3/18
 **/

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("testClass")
    public void testClass(){
        System.out.println("测试成功");
    }
}

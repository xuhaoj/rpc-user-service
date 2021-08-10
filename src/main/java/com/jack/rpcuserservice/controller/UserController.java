package com.jack.rpcuserservice.controller;

import com.jack.IGoodService;
import com.jack.IOrderService;
import com.jack.rpcuserservice.annotation.JackReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    //这里的作用是将接口封装成一个代理对象
    @JackReference
    private IOrderService orderService;

    @JackReference
    private IGoodService goodService;

    @GetMapping("/test")
    public String test() {
        return orderService.queryOrderList();
    }

    @GetMapping("/get")
    public String get() {
        return goodService.getGoodInfoById(1L);
    }
}

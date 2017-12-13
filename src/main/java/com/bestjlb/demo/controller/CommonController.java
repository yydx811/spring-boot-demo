package com.bestjlb.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yydx811 on 2017/11/1.
 */
@RestController
public class CommonController {

    @RequestMapping(value = {"/", "/index", "/home"})
    public String index() {
        return "hello!</br>" +
                "<form action='/login' method='POST'>" +
                "<div><label>username: <input type='text' name='username'/></label></div>" +
                "<div><label>password: <input type='password' name='password'/></label></div>" +
                "<div><input type='submit' value='sign in'/></div>" +
                "</form>";
    }

//    @RequestMapping(value = "/error")
    public String error() {
        return "error!";
    }
}

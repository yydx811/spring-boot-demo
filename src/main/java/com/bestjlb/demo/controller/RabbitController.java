package com.bestjlb.demo.controller;

import com.bestjlb.demo.config.DemoServiceEventSourceConfig;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yydx811 on 2017/10/26.
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitController {

    @Autowired
    private DemoServiceEventSourceConfig.DemoEventSource eventSource;

    @ApiOperation(value = "2.1 发送", notes = "发送rabbitmq消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "message", value = "rabbitmq message", defaultValue = "", required = true, paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success!")
    })
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendMessage(String message) {
        eventSource.publishToDemo(message);
    }
}

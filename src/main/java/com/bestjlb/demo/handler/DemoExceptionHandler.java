package com.bestjlb.demo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by yydx811 on 2017/10/26.
 */
@ControllerAdvice
public class DemoExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(DemoExceptionHandler.class);

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handlerIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        logger.info(ex.getMessage());
        return new ResponseEntity<>(false, new HttpHeaders(), HttpStatus.OK);
    }
}

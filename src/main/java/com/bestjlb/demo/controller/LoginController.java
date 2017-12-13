package com.bestjlb.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yydx811 on 2017/11/1.
 */
@RestController
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager manager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password) {
        try {
            SecurityContextHolder.getContext().setAuthentication(
                    manager.authenticate(new UsernamePasswordAuthenticationToken(username, password)));
            return "login successfully!</br>" +
                    "<a href='/swagger-ui.html'>debug</a>";
        } catch (AuthenticationException e) {
            logger.error("login failed!", e);
            return "login failed!";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        SecurityContextHolder.clearContext();
        return "logout successfully!</br>" +
                "<a href='/'>index</a>";
    }
}

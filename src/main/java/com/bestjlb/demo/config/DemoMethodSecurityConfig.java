package com.bestjlb.demo.config;

import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Created by yydx811 on 2017/11/2.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class DemoMethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new DefaultMethodSecurityExpressionHandler();
    }
}

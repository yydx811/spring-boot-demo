package com.bestjlb.demo.config.security;

import com.bestjlb.demo.utils.IPUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yydx811 on 2017/11/6.
 */
public class IpAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public IpAuthenticationProcessingFilter() {
        super("/ipLogin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String ip = IPUtils.getClientIpAddr(request);
        return getAuthenticationManager().authenticate(new IpAuthenticationToken(ip));
    }
}

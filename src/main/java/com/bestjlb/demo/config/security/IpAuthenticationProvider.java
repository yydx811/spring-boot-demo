package com.bestjlb.demo.config.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yydx811 on 2017/11/6.
 */
public class IpAuthenticationProvider implements AuthenticationProvider {

    private static final Map<String, List<GrantedAuthority>> IP_AUTHORITIES_MAP = new HashMap<>(2);
    static {
        IP_AUTHORITIES_MAP.put("127.0.0.1", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER")));
        IP_AUTHORITIES_MAP.put("192.168.1.54", Arrays.asList(new SimpleGrantedAuthority("ROLE_GUEST")));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String ip = (String) authentication.getPrincipal();
        if (IP_AUTHORITIES_MAP.containsKey(ip)) {
            return new IpAuthenticationToken(ip, IP_AUTHORITIES_MAP.get(ip));
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return IpAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

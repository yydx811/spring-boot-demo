package com.bestjlb.demo.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by yydx811 on 2017/11/6.
 */
public class IpAuthenticationToken extends AbstractAuthenticationToken {

    private String ip;

    public IpAuthenticationToken(String ip) {
        super(null);
        this.ip = ip;
        this.setAuthenticated(false);
    }

    public IpAuthenticationToken(String ip, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.ip = ip;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return ip;
    }
}

package com.bestjlb.demo;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yydx811 on 2017/10/31.
 */
public class AuthenticationDemo {

    private static AuthenticationManager authenManager = new SampleAuthenticationManager();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("please enter your username: ");
            String username = reader.readLine();
            System.out.println("please enter your password: ");
            String password = reader.readLine();
            try {
                Authentication authen = authenManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                SecurityContextHolder.getContext().setAuthentication(authen);
                break;
            } catch (AuthenticationException e) {
                System.out.println("authentication failed : " + e.getMessage());
            }
        }
        System.out.println("successfully authenticated. context : " + SecurityContextHolder.getContext().getAuthentication());
    }
}

class SampleAuthenticationManager implements AuthenticationManager {

    private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();
    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getName().equals(authentication.getCredentials())) {
            return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
        }
        throw new BadCredentialsException("bad credentials!");
    }
}

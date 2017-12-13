package com.bestjlb.demo.config;

import com.bestjlb.demo.config.security.IpAuthenticationProcessingFilter;
import com.bestjlb.demo.config.security.IpAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

/**
 * Created by yydx811 on 2017/11/1.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        auth.authenticationProvider(provider);
        auth.authenticationProvider(new IpAuthenticationProvider());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setDataSource(dataSource);
        return jdbcDao;
    }

    IpAuthenticationProcessingFilter ipAuthenticationProcessingFilter(AuthenticationManager authenManager) {
        IpAuthenticationProcessingFilter filter = new IpAuthenticationProcessingFilter();
//        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/error"));
//        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/swagger-ui.html"));
        filter.setAuthenticationManager(authenManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/", "/login", "/home", "/index", "/ipLogin").permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/").successForwardUrl("/swagger-ui.html").permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error")
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/ipLogin"))
                .and()
                .addFilterBefore(ipAuthenticationProcessingFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
}

package com.mikitjuk.advt.config;

import com.mikitjuk.advt.auth.JWTAuthorizationFilter;
import com.mikitjuk.advt.auth.JwtUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static com.mikitjuk.advt.config.SecurityConstants.API_AUTHENTICATE;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, API_AUTHENTICATE).permitAll()
                .antMatchers("/api/app*/**").access("hasAuthority('ADOPS') or hasAuthority('PUBLISHER')")
                .antMatchers("/api/user*/**").access("hasAuthority('ADOPS') or hasAuthority('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), getJwtUtil()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public JwtUtil getJwtUtil() {
        return new JwtUtil();
    }
}

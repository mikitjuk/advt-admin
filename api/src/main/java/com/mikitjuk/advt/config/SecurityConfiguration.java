package com.mikitjuk.advt.config;

import com.mikitjuk.advt.auth.JWTAuthorizationFilter;
import com.mikitjuk.advt.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
//@EnableAutoConfiguration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    public SecurityConfiguration(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


//    @Bean
//    public JWTAuthenticationFilter getJwtAuthenticationFilter() throws Exception {
//        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager());
//        jwtAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/authenticate","POST"));
//        return jwtAuthenticationFilter;
//    }

//    @Override
//    public void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.authenticationProvider(new CustomAuthenticationProvider());
//    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager());
//        jwtAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/authenticate","POST"));

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/**").permitAll();
                .antMatchers("/api/app/**", "/api/apps/**").access("hasAuthority('ADOPS') or hasAuthority('PUBLISHER')")
                .antMatchers("/api/user/**", "/api/users/**").access("hasAuthority('ADOPS') or hasAuthority('ADMIN')")
                .anyRequest().authenticated()
                .and()
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), getJwtUtil()))
//                 this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public JwtUtil getJwtUtil() {
        return new JwtUtil();
    }


}

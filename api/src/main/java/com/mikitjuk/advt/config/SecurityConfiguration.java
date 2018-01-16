package com.mikitjuk.advt.config;

import com.mikitjuk.advt.domain.User;
import com.mikitjuk.advt.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
////    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication().withUser("publisher").password("secret").roles(UserRole.PUBLISHER.name());
////        auth.inMemoryAuthentication().withUser("admin").password("secret").roles(UserRole.ADMIN.name());
////        auth.inMemoryAuthentication().withUser("adops").password("secret").roles(UserRole.ADOPS.name());
////    }

    @Autowired
    public UserDetailsService userDetailsService(){
        GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
        UserDetails userDetails = (UserDetails)new User("ram", "ram123", Arrays.asList(authority));
        return new InMemoryUserDetailsManager(Arrays.asList(userDetails));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/api/app/**" ,).hasRole("ADMIN")
                .antMatchers("/api/app/**", "/api/apps/**").access("hasRole('ADOPS') or hasRole('PUBLISHER')")
                .antMatchers("/api/user/**", "/api/users/**").access("hasRole('ADOPS') or hasRole('ADMIN')")
//                .antMatchers("/api").access("hasRole('ADOPS') or hasRole('ADMIN') or hasRole('PUBLISHER')")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

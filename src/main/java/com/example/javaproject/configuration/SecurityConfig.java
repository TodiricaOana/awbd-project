package com.example.javaproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Profile("h2")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin@awbd.com")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .and()
                .withUser("guest@awbd.com")
                .password(passwordEncoder().encode("password"))
                .roles("GUEST");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/products", "/products/*").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/products/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/products/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/products/delete/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/products/form/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/users/form").permitAll()
                .antMatchers(HttpMethod.GET,"/users/*", "/users/form/*").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.GET,"/users/current").hasAnyRole("GUEST", "ADMIN")
                .antMatchers(HttpMethod.DELETE,"/users/delete/**").hasAnyRole( "GUEST", "ADMIN")
                .antMatchers(HttpMethod.PUT,"/users/*").hasAnyRole( "GUEST", "ADMIN")
                .antMatchers(HttpMethod.POST,"/cart/*").hasAnyRole( "GUEST")
                .antMatchers(HttpMethod.PUT,"/cart/**").hasAnyRole( "GUEST")
                .antMatchers(HttpMethod.GET,"/cart").hasAnyRole( "GUEST")
                .antMatchers(HttpMethod.POST,"/orders").hasAnyRole( "GUEST")
                .antMatchers(HttpMethod.GET,"/orders/**").hasAnyRole( "ADMIN")
                .antMatchers(HttpMethod.PUT,"/orders/*").hasAnyRole( "ADMIN")
                .antMatchers(HttpMethod.DELETE,"/orders/delete/*").hasAnyRole( "ADMIN")
                .antMatchers(HttpMethod.POST,"/reviews").hasAnyRole( "GUEST")
                .antMatchers(HttpMethod.GET,"/reviews/form/*").hasAnyRole( "GUEST")
                .antMatchers(HttpMethod.GET,"/reviews/*").hasAnyRole( "GUEST", "ADMIN")
                .antMatchers(HttpMethod.DELETE,"/reviews/*").hasAnyRole("ADMIN")
                .and()
                .formLogin().loginPage("/showLogInForm")
                .loginProcessingUrl("/authUser")
                .failureUrl("/login-error").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");
    }
}

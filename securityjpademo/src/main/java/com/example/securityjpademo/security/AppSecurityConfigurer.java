package com.example.securityjpademo.security;

import com.example.securityjpademo.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    AppAuthenticationSuccessHandler appAuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("AppSecurityConfigurer configure auth......");
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("AppSecurityConfigurer configure http......");
        http.authorizeRequests()
                .antMatchers("/login","/css/**","/js/**","/img/*").permitAll()
                .antMatchers("/", "/home").hasRole("USER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "DBA")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").successHandler(appAuthenticationSuccessHandler)
                .usernameParameter("loginName").passwordParameter("password")
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
    }
}

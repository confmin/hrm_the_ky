package com.example.autherjava.config;

import com.example.autherjava.jwt.JwtFilter;
import com.example.autherjava.service.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private AccountServiceImp accountServiceImp ;



        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.cors().disable();
            http.csrf().disable();
            http.authorizeRequests()
                    .antMatchers("/api/**","/api/login")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            http.headers().frameOptions().sameOrigin();
            return http.build();
        }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
            return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
        }

//      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//          auth.userDetailsService(accountServiceImp).passwordEncoder(passwordEncoder());
//
//    }


    }


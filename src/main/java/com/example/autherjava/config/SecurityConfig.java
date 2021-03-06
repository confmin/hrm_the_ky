package com.example.autherjava.config;


import com.example.autherjava.security.jwt.JwtFilter;
import com.example.autherjava.utils.respon.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfig extends GlobalMethodSecurityConfiguration {
    @Autowired
    private JwtFilter jwtFilter;
        @Autowired
        private CustomEvaluatorPermisson permissionEvaluator;
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.cors().disable();
            http.csrf().disable();
            http.authorizeRequests()
                    .antMatchers("/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            http.headers().frameOptions().sameOrigin();
            return http.build();
        }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
            return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
        }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    }


package com.example.springsecurity.config;

import com.example.springsecurity.service.CustomUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableCaching
public class SecurityConfig {

    //userDetailService에 Bean이 정의 되어 있다면 -> Bean으로 정의 된 부분 사용
    //자동적으로 설정을 해준다.
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //DaoAuthenticationProvider 생성 -> custom하게 생성 하지 않았을 경우 생성

        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(new CustomAuthenticationProvider());
        builder.authenticationProvider(new CustomAuthenticationProvider2());

        http
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
               // .authenticationProvider(new CustomAuthenticationProvider())
               // .authenticationProvider(new CustomAuthenticationProvider2());
        return http.build(); // securityFilterChain 생성된다.
    }
}

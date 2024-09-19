package com.example.springsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import java.io.IOException;

//@Slf4j
//@Configuration
//@EnableCaching
//public class SecurityConfig {
//
//    //메모리 상에서 사용자 설정
//    //설정 파일과 중복이 있다면, @Bean 인메모리 방식이 우선
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//
//        //User객체는 SpringSecurity가 갖고 있는 User
//        UserDetails user = User.withUsername("user")
//                .password("{noop}1111") //(noop)을 사용하면 평서문 처럼 사용 가능
//                .authorities("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        //DaoAuthenticationProvider 생성 -> custom하게 생성 하지 않았을 경우 생성
//
//        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        builder.authenticationProvider(new CustomAuthenticationProvider());
//        builder.authenticationProvider(new CustomAuthenticationProvider2());
//
//        http
//                .authorizeHttpRequests(auth -> auth
//                        //.requestMatchers("/").permitAll()
//                        .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults());
//               // .authenticationProvider(new CustomAuthenticationProvider())
//               // .authenticationProvider(new CustomAuthenticationProvider2());
//        return http.build(); // securityFilterChain 생성된다.
//    }
//}

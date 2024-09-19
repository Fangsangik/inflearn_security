package com.example.springsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Slf4j
//@Configuration
//@EnableCaching
//public class SecurityConfig2 {
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
//    //하나의 Bean만 정의
//    @Bean
//    public AuthenticationProvider customAuthenticationProvider() {
//        return new CustomAuthenticationProvider();
//    }
//
//    //해당 Provider가 어디에 속해있는지가 중요
//    @Bean
//    public SecurityFilterChain securityFilterChain
//            (HttpSecurity http,
//             AuthenticationManagerBuilder builder,
//             AuthenticationConfiguration configuration) throws Exception {
//
//
//        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        managerBuilder.authenticationProvider(customAuthenticationProvider());
//
//        ProviderManager authenticationManager = (ProviderManager) configuration.getAuthenticationManager();
//        authenticationManager.getProviders().remove(0); //첫번쨰 꺼 삭제
//        builder.authenticationProvider(new DaoAuthenticationProvider());
//
//        //DaoAuthenticationProvider 생성 -> custom하게 생성 하지 않았을 경우 생성
//
//        http
//                .authorizeHttpRequests(auth -> auth
//                        //.requestMatchers("/").permitAll()
//                        .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults());
//        // .authenticationProvider(new CustomAuthenticationProvider())
//        // .authenticationProvider(new CustomAuthenticationProvider2());
//        return http.build(); // securityFilterChain 생성된다.
//    }
//}

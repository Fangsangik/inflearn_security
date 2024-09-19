package com.example.springsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableCaching
public class SecurityConfig3 {

    //메모리 상에서 사용자 설정
    //설정 파일과 중복이 있다면, @Bean 인메모리 방식이 우선
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        //User객체는 SpringSecurity가 갖고 있는 User
        UserDetails user = User.withUsername("user")
                .password("{noop}1111") //(noop)을 사용하면 평서문 처럼 사용 가능
                .authorities("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    //두개의 Bean 정의
    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider2() {
        return new CustomAuthenticationProvider2();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        //Bean 2개가 들어간다.
        builder.authenticationProvider(customAuthenticationProvider());
        builder.authenticationProvider(customAuthenticationProvider2());

        //DaoAuthenticationProvider 생성 -> custom하게 생성 하지 않았을 경우 생성

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

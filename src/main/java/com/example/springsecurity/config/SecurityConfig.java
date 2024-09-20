package com.example.springsecurity.config;

import com.example.springsecurity.service.CustomUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    //authenticationManager -> 일반 객체로 생성 했기에 Bean으로 굳이 주입 안해도 됨
    public CustomAuthenticationFilter customAuthenticationFilter(HttpSecurity httpSecurity,
                                                                 AuthenticationManager authenticationManager) {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(httpSecurity);
        customAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return customAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager manager = builder.build();

        //이상태에서 로그인 하면 인증 상태 지속 X
        //Session이 아니라 요청 객체로 저장 되기 때문에 저장이 되지 않음
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .authenticationManager(manager)
                //.securityContext(securityContext -> securityContext.requireExplicitSave(false)) // false이면 -> HttpSession에 저장
                .addFilterBefore(customAuthenticationFilter(http, manager),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build(); // securityFilterChain 생성된다.
    }
}

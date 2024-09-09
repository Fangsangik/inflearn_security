package com.example.springsecurity.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;


@Configuration
@EnableCaching
public class SecurityConfig {

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/logoutSuccess").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                SavedRequest savedRequest = requestCache.getRequest(request, response); //SavedRequest를 가져오고
                                String redirectUrl = savedRequest.getRedirectUrl(); //여러 값을 가져올 수 있다.
                                response.sendRedirect(redirectUrl);
                            }
                        })
                );


        return http.build(); // securityFilterChain 생성된다.
    }
}

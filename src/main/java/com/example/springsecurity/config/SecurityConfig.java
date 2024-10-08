package com.example.springsecurity.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
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

        http
                //SecurityFilterChainConfiguration -> filter 조건 성립 안됨
                //SecurityFilterChain -> Bean을 생성했기 때문 -> Bean이 없을 경우에만 성립된다. -> 사용자가 설정한 쪽으로 오게 된다.
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                //인증을 받지 못했을 경우 인증을 받을 수 있게 설정
                .formLogin(form -> form
                        //.loginPage("/loginPage") //-> 인증을 받지 못했을때 loginPage로 이동
                        .loginProcessingUrl("/loginProc")
                        //true -> root로 가게끔 설정  / false -> home으로 이동
                        //인증 이전에 보안이 필요한 페이지 방문 -> 인증 성공, 이전 위치로 다이렉트
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/failed")
                        .usernameParameter("userId")
                        .passwordParameter("password")

                        //custom 한 부분이 더 우선시 된다.
                        .successHandler(new AuthenticationSuccessHandler() { //지정한 클래스로
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                log.info("Authentication : {}", authentication);
                                //어디로 이동 할 것인지
                                response.sendRedirect("/home");
                            }
                        })
                        .failureHandler((request, response, exception) -> { //예외 정보가 남으면 된다.
                            log.warn("exception : {}", exception.getMessage());
                            response.sendRedirect("/login");
                        })
                        .permitAll()
                );
        // Customizer -> 커스텀 할 예정

        return http.build(); // securityFilterChain 생성된다.
    }
}

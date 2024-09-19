package com.example.springsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@EnableCaching
public class SecurityConfig2 {

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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/api/login")
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(customAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build(); // securityFilterChain 생성된다.
    }

    public CustomAuthenticationFilter customAuthenticationFilter(HttpSecurity http) throws Exception {
        List<AuthenticationProvider> providers = List.of(new DaoAuthenticationProvider());
        ProviderManager parent = new ProviderManager(providers);

        List<AuthenticationProvider> providers2 = List.of(new AnonymousAuthenticationProvider("key"), new CustomAuthenticationProvider());
        ProviderManager manager = new ProviderManager(providers2, parent);

        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(http);
        filter.setAuthenticationManager(manager);

        return filter;
    }
}

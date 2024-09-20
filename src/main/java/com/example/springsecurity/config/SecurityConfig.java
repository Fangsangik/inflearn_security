package com.example.springsecurity.config;

import com.example.springsecurity.service.CustomUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableCaching
public class SecurityConfig {

    //userDetailService에 Bean이 정의 되어 있다면 -> Bean으로 정의 된 부분 사용
    //자동적으로 설정을 해준다.
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return  new InMemoryUserDetailsManager(user);
    }

    //authenticationConfiguration을 통해 설정 클래스를 얻을 수 있다.
    // -> login에 사용되는 authenticationManager를 Bean으로 설정
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //이상태에서 로그인 하면 인증 상태 지속 X
        //Session이 아니라 요청 객체로 저장 되기 때문에 저장이 되지 않음
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated())
                //.formLogin(Customizer.withDefaults());
                //스프링 시큐리티는 변경을 위해서 던지는 요청 방식 -> HTTP 메서드 방식은 csrf 토큰을 요함 (악의적 변경 방지를 위해서 사용)
                .csrf(AbstractHttpConfigurer::disable);

        return http.build(); // securityFilterChain 생성된다.
    }
}

package com.example.springsecurity.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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


        http
                //SecurityFilterChainConfiguration -> filter 조건 성립 안됨
                //SecurityFilterChain -> Bean을 생성했기 때문 -> Bean이 없을 경우에만 성립된다. -> 사용자가 설정한 쪽으로 오게 된다.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/logoutSuccess").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                //.csrf(csrf -> csrf.disable()) //csrf 방식이 disable -> post 방식 뿐만 아니라 get 방식도 적용이 가능
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST")) //get 방식도 가능
                        .logoutSuccessUrl("/logoutSuccess") // 해당하는 Mapping 생성 logoutRequestMatcher 처리 안하면 post 방식만 가능
                        .logoutSuccessHandler(new LogoutSuccessHandler() {
                            @Override
                            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                response.sendRedirect("/logoutSuccess");
                                //복잡한 경로 -> handler 사용
                            }
                        })
                        .deleteCookies("JSESSIONID", "remember-me") //보통적으로 JSESSIONID는 삭제
                        .invalidateHttpSession(true) // logout시 session 무효화
                        .clearAuthentication(true) //securityContext 안에 있는 authentication 객체를 제거
                        .addLogoutHandler(new LogoutHandler() {
                            @Override
                            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                                //세션 무효화
                                HttpSession session = request.getSession();
                                session.invalidate();
                                //시큐리티 컨택스트 안에있는 authentication을 제거 가능 / 기존 authenticaiton 객체가 제거
                                SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(null);
                                SecurityContextHolder.getContextHolderStrategy().clearContext(); //SecurityContextHolder 또한 clear
                            }
                        })

                        .permitAll() //logoutProc
                );


        return http.build(); // securityFilterChain 생성된다.
    }
}

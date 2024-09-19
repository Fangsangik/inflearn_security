package com.example.springsecurity.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CustomAuthenticationProvider2 implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName();
        String password = authentication.getCredentials().toString();

        //아이디 검증
        //비밀번호 검증

        return new UsernamePasswordAuthenticationToken
                (loginId, password, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        //CollectionType으로 주어줘여 한다.
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //넘어온 타입과 인자로 들어가는 타입이 일치하는지 확인
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class); //-> true가 된다.
    }
}

package com.example.springsecurity.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

    public void SecurityContext () {
        // 현재 인증을 받은 SecurityContext가 넘어옴
        // 구문 자체에서 받아 올 수 있다.
        // 바로 참조가 가능하다.
        SecurityContext context = SecurityContextHolder.getContextHolderStrategy().getContext();
        Authentication authentication = context.getAuthentication();
        System.out.println("authentication = " + authentication);
    }
}

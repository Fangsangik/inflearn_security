package com.example.springsecurity.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/anonymous")
    public String anonymous() {
        return "anonymous";
    }

    //익명 객체가 참조 할 방법이 없음
    @GetMapping("/authentication")
    public String authentication(Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "anonymous";
        } else {
            return "not anonymous"; //인증 받지 못한 사용자도 호출 == null
        }
    }

    //인증 받지 못한 사용자도 객체를 갖게 된다. -> Guest라는 값을 갖는다.
    @GetMapping("/anonymousContext")
    public String anonymousContext(@CurrentSecurityContext SecurityContext securityContext) {
        return securityContext.getAuthentication().getName(); //익명 객체를 만들 수 있다.
    }

    @GetMapping("/logoutSuccess")
    public String logoutProc() {
        return "logoutSuccess";
    }
}

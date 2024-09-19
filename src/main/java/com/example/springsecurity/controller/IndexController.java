package com.example.springsecurity.controller;

import com.example.springsecurity.service.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    SecurityContextService securityContextService;

    @GetMapping("/")
    public Authentication index(Authentication authentication) {
        return authentication;
    }


    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}

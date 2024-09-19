package com.example.springsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountDto {
    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;
}

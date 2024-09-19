package com.example.springsecurity.service;

import com.example.springsecurity.dto.AccountDto;
import com.example.springsecurity.dto.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class CustomUserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AccountDto accountDto = new AccountDto("user", "{noop}1111", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        return new CustomUserDetails(accountDto);
    }
}

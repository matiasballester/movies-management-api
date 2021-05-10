package com.admiosflix.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class JwtUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admiosflix.user.username}")
    private String adminUserName;
    @Value("${admiosflix.user.password}")
    private String adminPassword;

    private String encodedAdminPassword;

    @PostConstruct
    public void postConstruct() {
        encodedAdminPassword = passwordEncoder.encode(adminPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(adminUserName, encodedAdminPassword, new ArrayList<>());
    }
}
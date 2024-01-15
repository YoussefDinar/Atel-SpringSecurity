package com.example.Springsecv2.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Service
class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    UserDetailsService userDetailsService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        Map<String, String> users = new HashMap<>();
        users.put("xproce", passwordEncoder.encode("12345"));
        if (users.containsKey(username))
            return new User(username, users.get(username), new ArrayList<>());

        throw new UsernameNotFoundException(username);
    }



    @Autowired
    public void configurePasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
// adding custom UserDetailsService and encryption bean to Authentication Manager
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        // Set other properties if needed
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
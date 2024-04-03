package com.navodaya.SpecialLogin.config;

import com.navodaya.SpecialLogin.filter.JwtAuthFilter;
import com.navodaya.SpecialLogin.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//    private static final String WELCOME_URL = "/auth/welcome";
//    private static final String ADD_NEW_USER_URL = "/auth/addNewUser";

    @Autowired
    private JwtAuthFilter authFilter;

    // User Creation // UserDetailsService is part of SpringSecurity which Takes UserInfoService created by us.
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .headers().cacheControl().disable().and()
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers(publicEndpoints()).permitAll()
//                .and()
//                .authorizeHttpRequests().requestMatchers("/auth/user/**").authenticated()
//                .and()
//                .authorizeHttpRequests().requestMatchers("/auth/admin/**").authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    private RequestMatcher publicEndpoints() {
//        return new OrRequestMatcher(
//                new AntPathRequestMatcher(WELCOME_URL),
//                new AntPathRequestMatcher(ADD_NEW_USER_URL),
//                // ... other public URLs
//        );
//    }


    // Configuring HttpSecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.headers().cacheControl().disable().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers( "/auth/users/**", "/auth/roles/**", "/auth/generateToken", "/auth/getRolesOfUser", "/menus/**", "/menus").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/auth/user/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/auth/admin/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    //By Default Authentication Manager is not Publically accessible. We are explicitally exposing it.
    //used to generateToken in UserController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}


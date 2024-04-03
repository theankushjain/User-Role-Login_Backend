package com.navodaya.SpecialLogin.utils;

import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.exception.TokenNotFoundException;
import com.navodaya.SpecialLogin.exception.UserNotFoundException;
import com.navodaya.SpecialLogin.repository.UserRepository;
import com.navodaya.SpecialLogin.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;

@Service
public class ExtractUserFromRequest {
        @Autowired
        JwtService jwtService;

        @Autowired
        UserRepository repository;

        public User extractCurrentUser(HttpServletRequest request) throws TokenNotFoundException {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
                return repository.findByEmail(username);
            }
            else{
                throw new TokenNotFoundException("Token not found in request:" + request);
            }
        }
}

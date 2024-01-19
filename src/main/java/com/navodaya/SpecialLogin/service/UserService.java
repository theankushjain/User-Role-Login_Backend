package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    String saveUser(User user);

    String updateUser(User user, Long userId);

    List<Role> getRolesOfUser(UserDetails userDetails);

    User findUserByEmail(String email);

    List<User> findAllUsers();

    List<Role> findAllRoles();

    public String softDeleteUser(Long userId);
}

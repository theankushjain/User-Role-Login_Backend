package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;

import java.util.List;

public interface UserService {
    String saveUser(User user);

    User findUserByEmail(String email);

    List<User> findAllUsers();

    List<Role> findAllRoles();
}

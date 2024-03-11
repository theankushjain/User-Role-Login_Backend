package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.dto.AddUserRequestDTO;
import com.navodaya.SpecialLogin.dto.UpdateUserRequestDTO;
import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    User saveUser(AddUserRequestDTO userRequest);

    User updateUser(UpdateUserRequestDTO user, Long userId);

    List<Role> getRolesOfUser(UserDetails userDetails);

    User findUserByEmail(String email);

    List<User> findAllUsers();

    List<Role> findAllRoles();

    public String softDeleteUser(Long userId);
}

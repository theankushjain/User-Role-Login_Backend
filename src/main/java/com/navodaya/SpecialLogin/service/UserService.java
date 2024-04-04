package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.dto.AddUserRequestDTO;
import com.navodaya.SpecialLogin.dto.UpdateUserRequestDTO;
import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(AddUserRequestDTO userRequest, User currentUser);

    User updateUser(UpdateUserRequestDTO user, Long userId, User currentUser) throws UserNotFoundException;

    List<Role> getRolesOfUser(UserDetails userDetails);

    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    List<User> findAllUsers();

    List<Role> findAllRoles();

    public User softDeleteUser(Long userId, User user) throws UserNotFoundException;
}

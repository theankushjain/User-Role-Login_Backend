package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> getAllRoles(); //Read

    String createRole(Role theRole); //Create

    String deleteRole(Long roleId); //Delete

    String updateRole(Role role, Long roleId);

    Role findByName(String name);

    Optional<Role> findById(Long roleId);

    List<User> usersAssociatedWithRole(Long roleId);

//    User removeUserFromRole(Long userId, Long roleId);
//
//    User assignUserToRole(Long userId, Long roleId);
//
//    Role removeAllUsersFromRole(Long roleId);
}

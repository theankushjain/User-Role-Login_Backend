package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.exception.RoleNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> getAllRoles(); //Read

    Role createRole(Role roleRequest); //Create

    String deleteRole(Long roleId); //Delete

    Role updateRole(Role roleRequest, Long roleId) throws com.navodaya.SpecialLogin.exception.RoleNotFoundException;

    Role findByName(String name);

    Optional<Role> findById(Long roleId);

    List<User> usersAssociatedWithRole(Long roleId);

//    User removeUserFromRole(Long userId, Long roleId);
//
//    User assignUserToRole(Long userId, Long roleId);
//
//    Role removeAllUsersFromRole(Long roleId);
}

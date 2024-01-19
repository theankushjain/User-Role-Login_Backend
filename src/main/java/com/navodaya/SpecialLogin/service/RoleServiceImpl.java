package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.repository.RoleRepository;
import com.navodaya.SpecialLogin.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.navodaya.SpecialLogin.service.JwtService;
import com.navodaya.SpecialLogin.filter.JwtAuthFilter;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private JwtService jwtService;
    private JwtAuthFilter jwtAuthFilter;
    private UserService userService;
    private String username;
    private User user;


    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    } //Read

    @Override
    public String createRole(Role theRole) {
//        Optional<Role> checkRole = Optional.ofNullable(roleRepository.findByName(theRole.getName()));
//        if (checkRole.isPresent()){
//            throw new RoleAlreadyExistsException(checkRole.get().getName()+ " role already exist");
//        }
        try {
            roleRepository.save(theRole);
            return "{\"Success\":\"Role Added\"}";
        } catch (Exception e) {
            // Handle the exception appropriately
            e.printStackTrace(); // or log the exception details
            return "{\"Error\": \"Unable to add role. Please try again.\"}";
        }
    }

    @Override
    public String deleteRole(Long roleId) {
//        this.removeAllUsersFromRole(roleId);
        if (usersAssociatedWithRole(roleId).isEmpty()){
            roleRepository.deleteById(roleId);
            return "{\"Success\": \"Deleted role Successfully.\"}";
        }
        else {
            return "{\"Error\": \"There are Users Associated with this Role. First Change Role of all such Users.\"}";
        }

    }

    @Override
    public String updateRole(Role updatedRoleData, Long roleId){
        try {
            Optional<Role> existingRoleOptional = roleRepository.findById(roleId);

            if (existingRoleOptional.isPresent()) {
                Role existingRole = existingRoleOptional.get();

                // Update the fields based on the incoming data
                existingRole.setName(updatedRoleData.getName());

                // Save the updated user
                createRole(existingRole);
            }
            return "{\"Success\":\"Role Updated\"}";
        } catch (Exception e) {
            // Handle exceptions appropriately
            return "{\"Error\":\"Role not Updated\"}";
        }
    }


    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<User> usersAssociatedWithRole(Long roleId){
        return roleRepository.findUsersByRoleId(roleId);
    }

    @Override
    public Optional<Role> findById(Long roleId){
        return roleRepository.findById(roleId);
    }

}

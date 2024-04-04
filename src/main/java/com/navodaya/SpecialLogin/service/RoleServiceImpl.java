package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.entity.Menu;
import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.exception.MenuNotFoundException;
import com.navodaya.SpecialLogin.exception.RoleNotFoundException;
import com.navodaya.SpecialLogin.repository.RoleRepository;
import com.navodaya.SpecialLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.navodaya.SpecialLogin.service.JwtService;
import com.navodaya.SpecialLogin.filter.JwtAuthFilter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
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
    public Role createRole(Role roleRequest) {
//        Optional<Role> checkRole = Optional.ofNullable(roleRepository.findByName(theRole.getName()));
//        if (checkRole.isPresent()){
//            throw new RoleAlreadyExistsException(checkRole.get().getName()+ " role already exist");
//        }
        Role role = Role.build(null, roleRequest.getName(),null);
        return roleRepository.save(role);
    }

    @Override
    public String deleteRole(Long roleId) {
//        Optional<Role> existingRoleOptional = roleRepository.findById(roleId);
//        if (existingRoleOptional.isPresent()) {
//            Role existingRole = existingRoleOptional.get();
//            Role updatedRole = Role.build(existingRole.getId(), existingRole.getName(),existingRole.getUsers(),true);
//            return roleRepository.save(updatedRole);
//        }
//        else {
//            throw new RoleNotFoundException("Role not found with id:" + roleId);
//        }
        if (usersAssociatedWithRole(roleId).isEmpty()){
            roleRepository.deleteById(roleId);
            return "{\"Success\": \"Deleted role Successfully.\"}";
        }
        else {
            return "{\"Error\": \"There are Users Associated with this Role. First Change Role of all such Users.\"}";
        }

    }

    @Override
    public Role updateRole(Role updatedRoleData, Long roleId) throws RoleNotFoundException {
        Optional<Role> existingRoleOptional = roleRepository.findById(roleId);
        if (existingRoleOptional.isPresent()) {
            Role existingRole = existingRoleOptional.get();
            Role updatedRole = Role.build(existingRole.getId(), updatedRoleData.getName(),existingRole.getUsers());
            return roleRepository.save(updatedRole);
        }
        else {
            throw new com.navodaya.SpecialLogin.exception.RoleNotFoundException("Role not found with id:" + roleId);
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

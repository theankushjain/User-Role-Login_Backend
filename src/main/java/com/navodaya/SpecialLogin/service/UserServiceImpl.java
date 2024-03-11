package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.dto.AddUserRequestDTO;
import com.navodaya.SpecialLogin.dto.UpdateUserRequestDTO;
import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.repository.RoleRepository;
import com.navodaya.SpecialLogin.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service  //suggests framework that the business logic resides here. Allows Autodetection of impl classes
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(AddUserRequestDTO userRequest) {
        List<Role> roles = userRequest.getRoles(); // Assuming this returns a list of role names
        List<Role> roleObjects = new ArrayList<>();
        for (Role role : roles) {
            Role foundRole = roleRepository.findByName(role.getName());
            if (foundRole != null) {
                roleObjects.add(foundRole);
            } else {
                System.out.println("not found");
            }
        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User user = User.build(null, userRequest.getName(), userRequest.getEmail(), userRequest.getPassword(), false, roleObjects);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserRequestDTO updatedUserData, Long userId) {
        Optional<User> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Update the fields based on the incoming data
            existingUser.setName(updatedUserData.getName());
            existingUser.setEmail(updatedUserData.getEmail());
            List<Role> roles = updatedUserData.getRoles();
            List<Role> roleObjects = new ArrayList<>();
            for (Role role : roles) {
                Role foundRole = roleRepository.findByName(role.getName());
                if (foundRole != null) {
                    roleObjects.add(foundRole);
                } else {
                    System.out.println("not found");
                }
            }
            User user = User.build(existingUser.getId(),existingUser.getName(), existingUser.getEmail(), existingUser.getPassword(), false, roleObjects);

            // Save the updated user
            return userRepository.save(user);
        }
        else{
            return null;
        }
    }
    @Override
    public List<Role> getRolesOfUser(UserDetails userDetails) {
        //Extract Username from Token
        User user = this.findUserByEmail(userDetails.getUsername());
//        We have extracted the user object whole roles are to be found.
        return userRepository.findRolesByUserId(user.getId());
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
         return userRepository.findAllNotDeleted(); //findAll function provided by JPARepository
    }

    @Override
    public List<Role> findAllRoles(){ return roleRepository.findAll();}

    @Override
    public String softDeleteUser(Long userId) {
        try{
            userRepository.softDelete(userId);
            return "{\"Success\":\"User Deleted\"}";
        }catch (Exception e){
            e.printStackTrace();
            return "{\"Error\":\"User not Deleted\"}";
        }
    }
}

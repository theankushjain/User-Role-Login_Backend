package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.repository.RoleRepository;
import com.navodaya.SpecialLogin.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String saveUser(User user) {
        List<Role> roles = user.getRoles();
        List<Role> fetchedRoles = new ArrayList<>();
        Role role = new Role();
        try {
            // encrypt the password using spring security
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            for (Role value : roles) {
                role = roleRepository.findByName(value.getName());
                if (role != null) {
                    fetchedRoles.add(role); // Collect roles
                }
            }
            //findByName function defined by us in roleRepository
//        if(role == null){
//            role = checkRoleExist();
//        }
            user.setRoles(fetchedRoles);
            userRepository.save(user);
            return "{\"Success\":\"User Added\"}";
        } catch (Exception e) {
            // Handle the exception appropriately
            e.printStackTrace(); // or log the exception details
            return "{\"Error\": \"Unable to add user. Please try again.\"}";
        }
    }

    @Override
    public String updateUser(User updatedUserData, Long userId) {
        try {
            Optional<User> existingUserOptional = userRepository.findById(userId);

            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();

                // Update the fields based on the incoming data
                existingUser.setName(updatedUserData.getName());
                existingUser.setEmail(updatedUserData.getEmail());
                existingUser.setPassword(updatedUserData.getPassword());
                existingUser.setRoles(updatedUserData.getRoles());

                // Save the updated user
                saveUser(existingUser);
            }
            return "{\"Success\":\"User Updated\"}";
        } catch (Exception e) {
            // Handle exceptions appropriately
            return "{\"Error\":\"User not Updated\"}";
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

package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.dto.AddUserRequestDTO;
import com.navodaya.SpecialLogin.dto.UpdateUserRequestDTO;
import com.navodaya.SpecialLogin.entity.Menu;
import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.exception.MenuNotFoundException;
import com.navodaya.SpecialLogin.exception.UserNotFoundException;
import com.navodaya.SpecialLogin.repository.RoleRepository;
import com.navodaya.SpecialLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service  //suggests framework that the business logic resides here. Allows Autodetection of impl classes
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(AddUserRequestDTO userRequest, User currentUser) {
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
        User user = User.build(null, userRequest.getName(), userRequest.getEmail(), userRequest.getPassword(), false, roleObjects, LocalDateTime.now(),LocalDateTime.now(), currentUser, currentUser);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserRequestDTO updatedUserData, Long userId, User currentUser) throws UserNotFoundException {
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
            User user = User.build(existingUser.getId(),existingUser.getName(), existingUser.getEmail(), existingUser.getPassword(), false, roleObjects, existingUser.getCreatedAt(), LocalDateTime.now(),existingUser.getCreatedBy(), currentUser);

            // Save the updated user
            return userRepository.save(user);
        }
        else{
            throw new UserNotFoundException("User not found with id:" + userId);
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
    public Optional<User> findUserById(Long id){
        return  userRepository.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
         return userRepository.findAllNotDeleted(); //findAll function provided by JPARepository
    }

    @Override
    public List<Role> findAllRoles(){ return roleRepository.findAll();}

    @Override
    public User softDeleteUser(Long userId, User user) throws UserNotFoundException{
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()){
            User deletedUser=new User();
            deletedUser = User.build(foundUser.get().getId(), foundUser.get().getName(), foundUser.get().getEmail(),foundUser.get().getPassword(),true,foundUser.get().getRoles(),foundUser.get().getCreatedAt(), LocalDateTime.now(),foundUser.get().getCreatedBy(),user);
            return userRepository.save(deletedUser);
        }else throw new UserNotFoundException("User not found with user Id: "+ userId);
    }
}

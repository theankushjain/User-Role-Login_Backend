package com.navodaya.SpecialLogin.controller;

import com.navodaya.SpecialLogin.dto.AddUserRequestDTO;
import com.navodaya.SpecialLogin.dto.UpdateUserRequestDTO;
import com.navodaya.SpecialLogin.entity.AuthRequest;
import com.navodaya.SpecialLogin.entity.JwtResponse;
import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.exception.UserNotFoundException;
import com.navodaya.SpecialLogin.service.JwtService;

import com.navodaya.SpecialLogin.service.UserService;
import com.navodaya.SpecialLogin.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@CrossOrigin
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUser(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @CrossOrigin
    @PostMapping("/users/add")
    public ResponseEntity<User> addNewUser(@RequestBody @Valid AddUserRequestDTO addUserInfo) {
        return new ResponseEntity<>(userService.saveUser(addUserInfo), CREATED);
    }

    @CrossOrigin
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> editUser(@RequestBody UpdateUserRequestDTO userInfo, @PathVariable Long userId) throws UserNotFoundException {
       return new ResponseEntity<>(userService.updateUser(userInfo, userId), OK);
    }

    @CrossOrigin
    @DeleteMapping("/users/{userId}")
    public int deleteUser(@PathVariable Long userId){
        return userService.softDeleteUser(userId) ;
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/roles")
    public List<Role> getRole(){
        return userService.findAllRoles();
    }

    @CrossOrigin (origins = "http://localhost:4200/")
    @PostMapping("/roles/add")
    public String addNewRole(@RequestBody Role roleInfo) { return roleService.createRole(roleInfo);}

    @CrossOrigin
    @PutMapping("/roles/{roleId}")
    public String editRole(@RequestBody Role roleInfo, @PathVariable Long roleId){
        String message = roleService.updateRole(roleInfo, roleId);
        System.out.println(message);
        return message;
    }

    @CrossOrigin
    @DeleteMapping("/roles/{roleId}")
    public String deleteRole(@PathVariable Long roleId){
        return roleService.deleteRole(roleId);
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/getRolesOfUser")
    public List<Role> getRolesOfUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getRolesOfUser(userDetails);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}

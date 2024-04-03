package com.navodaya.SpecialLogin.controller;

import com.navodaya.SpecialLogin.dto.MenuRequestDTO;
import com.navodaya.SpecialLogin.entity.Menu;

import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.exception.MenuNotFoundException;
import com.navodaya.SpecialLogin.exception.TokenNotFoundException;
import com.navodaya.SpecialLogin.service.MenuService;
import com.navodaya.SpecialLogin.utils.ExtractUserFromRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private ExtractUserFromRequest extractUserFromRequest;

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/")
    public ResponseEntity<List<Menu>> getMenus(){
        return ResponseEntity.ok(menuService.getAllMenus());
    }

    @CrossOrigin (origins = "http://localhost:4200/")
    @PostMapping("/add")
    public ResponseEntity<Menu> addNewMenu(HttpServletRequest request, @RequestBody @Valid MenuRequestDTO addMenuInfo) throws TokenNotFoundException {
        User user =  extractUserFromRequest.extractCurrentUser(request);
        return new ResponseEntity<>(menuService.createMenu(addMenuInfo, user), CREATED);
    }

    @CrossOrigin
    @PutMapping("/{menuId}")
    public ResponseEntity<Menu> editMenu(HttpServletRequest request, @RequestBody @Valid MenuRequestDTO editMenuInfo, @PathVariable Long menuId) throws MenuNotFoundException,TokenNotFoundException {
        User user = extractUserFromRequest.extractCurrentUser(request);
        return new ResponseEntity<>(menuService.updateMenu(editMenuInfo, menuId, user), OK);
    }

    @CrossOrigin
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Menu> deleteMenu(HttpServletRequest request, @PathVariable Long menuId) throws TokenNotFoundException, MenuNotFoundException {
        User user=extractUserFromRequest.extractCurrentUser(request);
        return new ResponseEntity<>(menuService.softDeleteMenu(menuId, user), OK);
    }

}


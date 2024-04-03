package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.dto.MenuRequestDTO;
import com.navodaya.SpecialLogin.entity.Menu;
import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.exception.MenuNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MenuService {
    List<Menu> getAllMenus(); //Read

    Menu createMenu(MenuRequestDTO theMenu, User currentUser); //Create

    Menu softDeleteMenu(Long menuId, User currentUser) throws MenuNotFoundException; //Delete

    Menu updateMenu(MenuRequestDTO menuRequest, Long menuId, User currentUser) throws MenuNotFoundException;

//    Menu findByName(String name);
//
//    Optional<Menu> findById(Long menuId);
}

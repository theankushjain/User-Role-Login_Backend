package com.navodaya.SpecialLogin.service;

import com.navodaya.SpecialLogin.dto.MenuRequestDTO;
import com.navodaya.SpecialLogin.entity.Menu;
import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import com.navodaya.SpecialLogin.exception.MenuNotFoundException;
import com.navodaya.SpecialLogin.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuRepository menuRepository;
    public List<Menu> getAllMenus(){
        return menuRepository.findAllNotDeleted();
    }

    public Menu createMenu(MenuRequestDTO menuRequest, User currentUser){
        Menu parentMenu = menuRequest.getParentMenu(); // Assuming this returns a list of role names
        Menu foundMenu = new Menu();
        if (parentMenu!=null){
            foundMenu = menuRepository.findByLabel(parentMenu.getLabel());
        }else {
            foundMenu=null;
        }

//        User user = userService.findUserByEmail(userDetails.getUsername());
        Menu menu = Menu.build(null, menuRequest.getLabel(), menuRequest.getIcon(),foundMenu,menuRequest.getOrdering(), menuRequest.getRouteLink(),false, LocalDateTime.now(),LocalDateTime.now(), currentUser, currentUser);
        return menuRepository.save(menu);
    }

    public Menu softDeleteMenu(Long menuId, User currentUser) throws MenuNotFoundException{
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent()){
            Menu deletedMenu=new Menu();
            deletedMenu = Menu.build(menu.get().getId(), menu.get().getLabel(), menu.get().getIcon(), menu.get().getParentMenu(), menu.get().getOrdering(),menu.get().getRouteLink(),true,menu.get().getCreatedAt(),LocalDateTime.now(),menu.get().getCreatedBy(),currentUser);
            return menuRepository.save(deletedMenu);
        }else throw new MenuNotFoundException("Menu not found with menu Id: "+ menuId);
    }

    public Menu updateMenu(MenuRequestDTO menuRequest, Long menuId, User currentUser) throws MenuNotFoundException{
        Optional<Menu> existingMenuOptional = menuRepository.findById(menuId);
        if (existingMenuOptional.isPresent()) {
            Menu existingMenu = existingMenuOptional.get();
            existingMenu = Menu.build(existingMenu.getId(), menuRequest.getLabel(),menuRequest.getIcon(),menuRequest.getParentMenu(),menuRequest.getOrdering(),menuRequest.getRouteLink(),false,existingMenu.getCreatedAt(),LocalDateTime.now(),existingMenu.getCreatedBy(),currentUser);
            return menuRepository.save(existingMenu);
        }
        else {
            throw new MenuNotFoundException("Menu not found with id:" + menuId);
        }

    }

//    Menu findByName(String name){
//        return menuRepository.findByName(name);
//    }
//
//    Optional<Menu> findById(Long menuId);
}

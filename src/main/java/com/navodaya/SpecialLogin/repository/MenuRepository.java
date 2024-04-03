package com.navodaya.SpecialLogin.repository;

import com.navodaya.SpecialLogin.entity.Menu;
import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    Menu findByLabel(String label);

    @Transactional
    @Modifying
    @Query("UPDATE Menu m SET m.deleted = true WHERE m.id = :menuId")
    int softDelete(@Param("menuId") Long menuId);

    @Query("SELECT m FROM Menu m WHERE m.deleted = false")
    List<Menu> findAllNotDeleted();
}

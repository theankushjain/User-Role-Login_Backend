package com.navodaya.SpecialLogin.repository;

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
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.deleted = false")
    List<User> findAllNotDeleted();

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.deleted = true WHERE u.id = :userId")
    void softDelete(@Param("userId") Long userId);


    @Query("SELECT u.roles FROM User u WHERE u.id = :userId")
    List<Role> findRolesByUserId(@Param("userId") Long userId);
}

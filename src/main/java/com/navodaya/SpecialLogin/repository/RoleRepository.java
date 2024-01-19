package com.navodaya.SpecialLogin.repository;

import com.navodaya.SpecialLogin.entity.Role;
import com.navodaya.SpecialLogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);

    @Query("SELECT r.users FROM Role r WHERE r.id = :roleId")
    List<User> findUsersByRoleId(@Param("roleId") Long roleId);
}

package com.navodaya.SpecialLogin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity   //specified that the class is mapped to a db table
@Table(name="users") //Name of DB Table that the Class is mapped to
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class User
{
    @Id  //Primary Key for DB Table
    @GeneratedValue(strategy = GenerationType.IDENTITY) //primary key value to be auto generated
    private Long id;

    @Column(nullable=false) //field cannot be null
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(name = "is_deleted")
    private boolean deleted = false;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL) //relation bw user and role is many-to-many
    @JoinTable(  //specifies name of join table and foreign keys for join table
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();

    // Logging fields
    @Column(nullable=false, unique=true, name="created_at")
    private LocalDateTime createdAt;
    @Column(nullable=false, unique=true, name="updated_at")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "createdby_id")
    @ManyToOne
    private User createdBy;

    @JoinColumn(name = "updatedby_id")
    @ManyToOne
    private User updatedBy;
}

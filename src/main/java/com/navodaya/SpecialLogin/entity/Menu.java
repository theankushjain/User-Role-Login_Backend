package com.navodaya.SpecialLogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="menus")
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String label;

    private String icon;

    @JoinColumn(name = "parent_id")
    @ManyToOne
    private Menu parentMenu;

    @Column(nullable=false, unique=false)
    private int ordering;

    @Column(nullable=false, unique=false)
    private String routeLink;

    @Column(name = "is_deleted")
    private boolean deleted= false;

    // Logging fields
    @Column(nullable=false, unique=true)
    private LocalDateTime createdAt;
    @Column(nullable=false, unique=true)
    private LocalDateTime updatedAt;

    @JoinColumn(name = "createdby_id")
    @ManyToOne
    private User createdBy;

    @JoinColumn(name = "updatedby_id")
    @ManyToOne
    private User updatedBy;

}

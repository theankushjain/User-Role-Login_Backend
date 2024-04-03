package com.navodaya.SpecialLogin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.navodaya.SpecialLogin.entity.Menu;
import com.navodaya.SpecialLogin.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequestDTO {

    @NotBlank(message = "Label Name is required")
    private String label;

    private String icon;

    private Menu parentMenu = null;

    private int ordering;

    @NotBlank(message = "Please enter the link to which menu should redirect")
    private String routeLink;

}

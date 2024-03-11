package com.navodaya.SpecialLogin.dto;

import com.navodaya.SpecialLogin.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UpdateUserRequestDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Format")
    private String email;

    @NotBlank(message = "At least 1 Role is required")
    private List<Role> roles;
}

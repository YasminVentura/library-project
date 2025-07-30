package com.example.library_system.controllers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank
        String name,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}

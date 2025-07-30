package com.example.library_system.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDTO(
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotBlank
        String publisher,
        @NotNull
        Integer publicationYear
) {
}



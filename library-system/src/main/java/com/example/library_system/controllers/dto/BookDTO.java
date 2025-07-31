package com.example.library_system.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.ISBN;

public record BookDTO(
        @ISBN
        @NotBlank
        String isbn,
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



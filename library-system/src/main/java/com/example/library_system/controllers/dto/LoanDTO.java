package com.example.library_system.controllers.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record LoanDTO(
        @NotNull
        UUID user,
        @NotNull
        UUID book,
        @NotNull
        @Future(message = "Future dates only")
        LocalDate dueDate) {
}

package com.example.library_system.controllers.dto;

import java.time.LocalDate;
import java.util.UUID;

public record LoanRegisterResponseDTO(
        UUID userId,
        String userName,
        String bookIsbn,
        String bookTitle,
        LocalDate loanDate,
        LocalDate dueDate
) {
}

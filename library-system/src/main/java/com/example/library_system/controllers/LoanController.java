package com.example.library_system.controllers;

import com.example.library_system.controllers.dto.LoanDTO;
import com.example.library_system.controllers.dto.LoanRegisterResponseDTO;
import com.example.library_system.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;


    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoanRegisterResponseDTO> registerLoan(@Valid @RequestBody LoanDTO dto) {
        LoanRegisterResponseDTO response = loanService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

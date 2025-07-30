package com.example.library_system.controllers;

import com.example.library_system.controllers.dto.BookDTO;
import com.example.library_system.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/register")
    public ResponseEntity<BookDTO> register(@Valid @RequestBody BookDTO dto) {
        bookService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}

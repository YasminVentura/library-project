package com.example.library_system.controllers;

import com.example.library_system.controllers.dto.BookDTO;
import com.example.library_system.entities.enums.BookStatus;
import com.example.library_system.services.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/allBooks")
    public  ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable UUID id, @RequestBody BookDTO dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDTO>> search(
            @RequestParam (value = "isbn", required = false) String isbn,
            @RequestParam (value = "title",  required = false) String title,
            @RequestParam (value = "author", required = false) String author,
            @RequestParam (value = "publisher", required = false) String publisher,
            @RequestParam(value = "status", required = false) BookStatus status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(name="sizePage", defaultValue = "20") Integer sizePage
    ) {
        return  ResponseEntity.ok(bookService
                    .search(isbn, title, author, publisher, status, page, sizePage));
    }

}

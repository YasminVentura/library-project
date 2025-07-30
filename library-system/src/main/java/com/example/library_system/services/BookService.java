package com.example.library_system.services;

import com.example.library_system.controllers.dto.BookDTO;
import com.example.library_system.controllers.mappers.BookMapper;
import com.example.library_system.entities.Book;
import com.example.library_system.entities.enums.BookStatus;
import com.example.library_system.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public void save(BookDTO dto) {
        Book book = bookMapper.toEntity(dto);
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);
    }
}

package com.example.library_system.services;

import com.example.library_system.controllers.dto.BookDTO;
import com.example.library_system.controllers.mappers.BookMapper;
import com.example.library_system.entities.Book;
import com.example.library_system.entities.enums.BookStatus;
import com.example.library_system.exceptions.custom.BookNotFoundException;
import com.example.library_system.exceptions.custom.IsbnAlreadyExistsException;
import com.example.library_system.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public void save(BookDTO dto) {
        if(bookRepository.existsByIsbn(dto.isbn())) {
            throw new IsbnAlreadyExistsException("Isbn is already in use: " + dto.isbn());
        }

        Book book = bookMapper.toEntity(dto);
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);
    }

    public BookDTO getBookById(UUID id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }
    public List<BookDTO> getAllBooks() {
        return  bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO update(UUID id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        if(!book.getIsbn().equals(dto.isbn()) && bookRepository.existsByIsbn(dto.isbn())) {
            throw new IsbnAlreadyExistsException("Isbn is already in use: " + dto.isbn());
        }

        book.setIsbn(dto.isbn());
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setPublisher(dto.publisher());
        book.setPublicationYear(dto.publicationYear());

        return bookMapper.toDTO(bookRepository.save(book));
    }

    public void delete(UUID id) {
        bookRepository.deleteById(id);
    }
}

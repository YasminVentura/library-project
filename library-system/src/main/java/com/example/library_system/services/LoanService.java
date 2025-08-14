package com.example.library_system.services;

import com.example.library_system.controllers.dto.LoanDTO;
import com.example.library_system.controllers.dto.LoanRegisterResponseDTO;
import com.example.library_system.controllers.mappers.LoanMapper;
import com.example.library_system.entities.Book;
import com.example.library_system.entities.Loan;
import com.example.library_system.entities.User;
import com.example.library_system.entities.enums.BookStatus;
import com.example.library_system.exceptions.custom.BookNotFoundException;
import com.example.library_system.exceptions.custom.BookUnavailableException;
import com.example.library_system.exceptions.custom.UserNotFoundException;
import com.example.library_system.repositories.BookRepository;
import com.example.library_system.repositories.LoanRepository;
import com.example.library_system.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.loanMapper = loanMapper;
    }

    @Transactional
    public LoanRegisterResponseDTO register(LoanDTO dto) {
        User user = userRepository.findById(dto.user())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + dto.user()));
        Book book = bookRepository.findById(dto.book())
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + dto.book()));


        if (BookStatus.BORROWED.equals(book.getStatus())) {
            throw new BookUnavailableException("Book is not available for loan: " + dto.book());
        }
        book.setStatus(BookStatus.BORROWED);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setDueDate(dto.dueDate());
        loan.setLoanDate(LocalDate.now());
        loan.setLateFee(BigDecimal.ZERO);

        bookRepository.save(book);
        return loanMapper.toResponseDTO(loanRepository.save(loan));
    }
}

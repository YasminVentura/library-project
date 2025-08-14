package com.example.library_system.controllers.mappers;

import com.example.library_system.controllers.dto.LoanRegisterResponseDTO;
import com.example.library_system.entities.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "bookIsbn", source = "book.isbn")
    @Mapping(target = "bookTitle", source = "book.title")
    LoanRegisterResponseDTO toResponseDTO(Loan loan);
}

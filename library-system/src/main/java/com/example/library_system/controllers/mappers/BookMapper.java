package com.example.library_system.controllers.mappers;

import com.example.library_system.controllers.dto.BookDTO;
import com.example.library_system.entities.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookDTO dto);

    BookDTO toDTO(Book book);
}

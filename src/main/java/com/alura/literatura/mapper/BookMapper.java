package com.alura.literatura.mapper;

import com.alura.literatura.dto.BookDTO;
import com.alura.literatura.dto.BooksPageDTO;
import com.alura.literatura.dto.PersonDTO;
import com.alura.literatura.model.Person;
import com.alura.literatura.service.BooksPage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.awt.print.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO toDTO(Book book);
    PersonDTO toDTO(Person p);

    BooksPageDTO pageToDTO(BooksPage page);
}

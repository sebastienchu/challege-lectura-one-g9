package com.alura.literatura.controller;

import com.alura.literatura.model.BookEntity;
import com.alura.literatura.repository.BookRepository;
import com.alura.literatura.service.GutenbergService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class GutenbergController {

    @Autowired
    private BookRepository repository;
    private final GutenbergService service;

    // Buscar y guardar si es necesario
    @GetMapping("/libro/{id}")
    public ResponseEntity<?> getBook(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getBookById(id));
    }


    @GetMapping("/search/title")
    public List<BookEntity> searchByTitle(@RequestParam String q) {
        return repository.findByTitleContainingIgnoreCase(q);
    }

    @GetMapping("/search/author")
    public List<BookEntity> searchByAuthor(@RequestParam String q) {
        return repository.findByAuthorName(q);
    }


    @GetMapping("/search/language")
    public List<BookEntity> searchByLanguage(@RequestParam String lang) {
        return repository.findByLanguageCode(lang);
    }
}

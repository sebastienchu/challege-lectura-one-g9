package com.alura.literatura.literatura.controller;

import com.alura.literatura.literatura.dto.BookDTO;
import com.alura.literatura.literatura.model.Book;
import com.alura.literatura.literatura.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping("/search")
    public BookDTO buscarYGuardar(@RequestParam String title) {
        Book libro = service.buscarYGuardarComoEntidad(title);
        return service.convertirADTO(libro); // ahora funciona porque es public
    }

    @GetMapping
    public List<Book> listar() {
        return service.listarTodosConAutoresEIdiomas();
    }
}


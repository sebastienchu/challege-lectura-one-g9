package com.alura.literatura.service;

import lombok.Data;

import java.awt.print.Book;
import java.util.List;

@Data
public class BooksPage {
    private Integer count;
    private String next;
    private String previous;
    private List<Book> results;
}

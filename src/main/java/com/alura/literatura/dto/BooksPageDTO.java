package com.alura.literatura.dto;

import lombok.Data;
import java.util.List;

@Data
public class BooksPageDTO {
    private Integer count;
    private String next;
    private String previous;
    private List<BookDTO> results;
}

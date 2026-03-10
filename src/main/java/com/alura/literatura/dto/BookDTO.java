package com.alura.literatura.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class BookDTO {
    private Integer id;
    private String title;
    private List<String> subjects;
    private List<PersonDTO> authors;
    private List<String> summaries;
    private List<PersonDTO> translators;
    private List<String> bookshelves;
    private List<String> languages;
    private Boolean copyright;
    private String media_type;
    private Map<String, String> formats;
    private Integer download_count;
}

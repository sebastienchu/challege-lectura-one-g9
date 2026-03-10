package com.alura.literatura.literatura.dto;

import com.alura.literatura.literatura.model.Person;

import java.util.List;
import java.util.Map;

public record BookDTO(
        Long id,
        String title,
        List<String> subjects,
        List<Person> authors,
        List<String> summaries,
        List<Person> translators,
        List<String> bookshelves,
        List<String> languages,
        Boolean copyright,
        String media_type,
        Map<String, String> formats,
        Integer download_count) {
}

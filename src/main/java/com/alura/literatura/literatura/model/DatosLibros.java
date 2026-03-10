package com.alura.literatura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String title,
        @JsonAlias("subjects") List<String> subjects,
        @JsonAlias("authors") List<DatosPerson> authors,
        @JsonAlias("summaries") List<String> summaries,
        @JsonAlias("translators") List<DatosPerson> translators,
        @JsonAlias("bookshelves") List<String> bookshelves,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("copyright") Boolean copyright,
        @JsonAlias("media_type") String media_type,
        @JsonAlias("formats") Map<String, String> formats,
        @JsonAlias("download_count") Integer downloadCount
) {}



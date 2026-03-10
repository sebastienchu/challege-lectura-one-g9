package com.alura.literatura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosPerson(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer birthYear,
        @JsonAlias("death_year") Integer deathYear
) {}

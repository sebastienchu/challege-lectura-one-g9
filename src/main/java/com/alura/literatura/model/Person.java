package com.alura.literatura.model;

import lombok.Data;

@Data
public class Person {
    private Integer birth_year;
    private Integer death_year;
    private String name;
}

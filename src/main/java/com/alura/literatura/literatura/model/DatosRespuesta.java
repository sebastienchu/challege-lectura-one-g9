package com.alura.literatura.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuesta(
        Integer count,
        String next,
        String previous,
        List<DatosLibros> results
) {}

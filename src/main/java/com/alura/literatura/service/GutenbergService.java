package com.alura.literatura.service;

import com.alura.literatura.dto.GutendexBookDTO;
import com.alura.literatura.mapper.GutendexMapper;
import com.alura.literatura.model.BookEntity;
import com.alura.literatura.model.FormatEntity;
import com.alura.literatura.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GutenbergService {

    @Autowired
    private final RestTemplate restTemplate;
    private final BookRepository bookRepository;
    private final GutendexMapper mapper;

    private static final String API = "https://gutendex.com/books/";

    @Transactional
    public BookEntity getOrFetchBook(Integer id) {
        return bookRepository.findById(id).orElseGet(() -> {
            GutendexBookDTO dto = restTemplate.getForObject(API + id, GutendexBookDTO.class);
            if (dto == null) throw new RuntimeException("Libro no encontrado en Gutendex");

            BookEntity entity = mapper.toEntity(dto);

            // manejar formats: Map<mime,url> -> FormatEntity linked to BookEntity
            Map<String, String> fmts = dto.getFormats();
            if (fmts != null && !fmts.isEmpty()) {
                Set<FormatEntity> formats = fmts.entrySet().stream().map(e -> {
                    FormatEntity f = new FormatEntity();
                    f.setMime(e.getKey());
                    f.setUrl(e.getValue());
                    f.setBook(entity);
                    return f;
                }).collect(Collectors.toSet());
                entity.setFormats(formats);
            }

            // Persistir (automerge de relaciones por cascades)
            return bookRepository.save(entity);
        });
    }
    public BookEntity getBookById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));
    }
}

package com.alura.literatura.mapper;

import com.alura.literatura.dto.GutendexBookDTO;
import com.alura.literatura.dto.PersonDTO;
import com.alura.literatura.model.*;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class GutendexMapper {

    @Autowired
    protected com.alura.literatura.repository.AuthorRepository authorRepository;

    @Autowired
    protected com.alura.literatura.repository.LanguageRepository languageRepository;

    @Autowired
    protected com.alura.literatura.repository.SubjectRepository subjectRepository;

    @Mapping(target = "mediaType", source = "media_type")
    @Mapping(target = "downloadCount", source = "download_count")
    @Mapping(target = "formats", ignore = true) // se manejará manualmente
    public abstract BookEntity toEntity(GutendexBookDTO dto);

    protected Set<AuthorEntity> mapAuthors(java.util.List<PersonDTO> authors) {
        if (authors == null) return java.util.Collections.emptySet();
        return authors.stream().map(p -> {
            return authorRepository.findByNameIgnoreCase(p.getName())
                    .orElseGet(() -> {
                        AuthorEntity a = new AuthorEntity();
                        a.setName(p.getName());
                        a.setBirthYear(p.getBirth_year());
                        a.setDeathYear(p.getDeath_year());
                        return a;
                    });
        }).collect(Collectors.toSet());
    }

    protected Set<LanguageEntity> mapLanguages(java.util.List<String> codes) {
        if (codes == null) return java.util.Collections.emptySet();
        return codes.stream().map(code ->
                languageRepository.findByCodeIgnoreCase(code)
                        .orElseGet(() -> {
                            LanguageEntity l = new LanguageEntity();
                            l.setCode(code);
                            return l;
                        })
        ).collect(Collectors.toSet());
    }

    protected Set<SubjectEntity> mapSubjects(java.util.List<String> subjects) {
        if (subjects == null) return java.util.Collections.emptySet();
        return subjects.stream().map(name ->
                subjectRepository.findByNameIgnoreCase(name)
                        .orElseGet(() -> {
                            SubjectEntity s = new SubjectEntity();
                            s.setName(name);
                            return s;
                        })
        ).collect(Collectors.toSet());
    }
}

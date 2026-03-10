package com.alura.literatura.literatura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Book {

    @Id
    private Long id; // como viene de Gutendex, NO se autogenera

    private String title;

    @ElementCollection
    private List<String> subjects;

//    @OneToMany(cascade = CascadeType.ALL)
//    private Set<Person> authors;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Person> authors;

    @ElementCollection
    @CollectionTable(name = "book_summaries", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "summaries")
    @Lob
    private List<String> summaries;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Person> translators;

    @ElementCollection
    private List<String> bookshelves;

    @ElementCollection
    private Set<String> languages; // Cambiado a Set

    private Boolean copyright;
    private String media_type;

    @ElementCollection
    private Map<String, String> formats;

    @Column(name = "download_count")
    private Integer downloadCount;


    public Book() {}

    public Book(DatosLibros datos) {
        this.id = datos.id();
        this.title = datos.title();
        this.subjects = datos.subjects();

        this.authors = datos.authors() != null
                ? datos.authors().stream()
                .map(a -> new Person(a.name(), a.birthYear(), a.deathYear()))
                .collect(Collectors.toSet())
                : null;

        this.summaries = datos.summaries();

        this.translators = datos.translators() != null
                ? datos.translators().stream()
                .map(t -> new Person(t.name(), t.birthYear(), t.deathYear()))
                .toList()
                : null;

        this.bookshelves = datos.bookshelves();

        this.languages = datos.languages() != null
                ? Set.copyOf(datos.languages())
                : null;

        this.copyright = datos.copyright();
        this.media_type = datos.media_type();
        this.formats = datos.formats();
        this.downloadCount = datos.downloadCount();

    }

    @Override
    public String toString() {
        return String.format(
                "Book[id=%d, title='%s', downloads=%d, language(s)=%s, authorsCount=%d]",
                id,
                title,
                downloadCount,
                (languages != null ? String.join(", ", languages) : "Sin idioma"),
                (authors != null ? authors.size() : 0)
        );
    }

    // Getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }

    public Set<Person> getAuthors() { return authors; }
    public void setAuthors(Set<Person> authors) { this.authors = authors; }

    public List<String> getSummaries() { return summaries; }
    public void setSummaries(List<String> summaries) { this.summaries = summaries; }

    public List<Person> getTranslators() { return translators; }
    public void setTranslators(List<Person> translators) { this.translators = translators; }

    public List<String> getBookshelves() { return bookshelves; }
    public void setBookshelves(List<String> bookshelves) { this.bookshelves = bookshelves; }

    public Set<String> getLanguages() { return languages; }
    public void setLanguages(Set<String> languages) { this.languages = languages; }

    public Boolean getCopyright() { return copyright; }
    public void setCopyright(Boolean copyright) { this.copyright = copyright; }

    public String getMedia_type() { return media_type; }
    public void setMedia_type(String media_type) { this.media_type = media_type; }

    public Map<String, String> getFormats() { return formats; }
    public void setFormats(Map<String, String> formats) { this.formats = formats; }

    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }
}

package com.alura.literatura.literatura.service;

import com.alura.literatura.literatura.dto.BookDTO;
import com.alura.literatura.literatura.model.Book;
import com.alura.literatura.literatura.model.Person;
import com.alura.literatura.literatura.model.DatosLibros;
import com.alura.literatura.literatura.model.DatosRespuesta;
import com.alura.literatura.literatura.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final String URL_BASE = "https://gutendex.com/books/";

    private final BookRepository repository;
    private final ConsumoApi consumoApi;
    private final ConvierteDatos conversor;

    public BookService(BookRepository repository) {
        this.repository = repository;
        this.consumoApi = new ConsumoApi();
        this.conversor = new ConvierteDatos();
    }

    // ----------------------
    // Búsqueda y guardado
    // ----------------------
    public Book buscarYGuardarComoEntidad(String title) {
        var url = URL_BASE + "?search=" + title.replace(" ", "+");
        var json = consumoApi.obtenerDatos(url);

        DatosRespuesta respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (respuesta.results().isEmpty()) {
            throw new RuntimeException("❌ No se encontraron libros con ese título.");
        }

        DatosLibros datos = respuesta.results().get(0);

        if (repository.existsById(datos.id())) {
            throw new RuntimeException("⚠️ El libro ya existe en la base de datos.");
        }

        Book book = new Book(datos);

        if (book.getSummaries() != null && !book.getSummaries().isEmpty()) {
            List<String> limpios = book.getSummaries().stream()
                    .map(s -> s.length() > 10000 ? s.substring(0, 10000) : s)
                    .toList();
            book.setSummaries(limpios);
        }

        repository.save(book);
        return book;
    }

    public BookDTO convertirADTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getSubjects(),
                book.getAuthors() != null ? new ArrayList<>(book.getAuthors()) : List.of(),
                book.getSummaries(),
                book.getTranslators() != null ? new ArrayList<>(book.getTranslators()) : List.of(),
                book.getBookshelves(),
                book.getLanguages() != null ? new ArrayList<>(book.getLanguages()) : List.of(),
                book.getCopyright(),
                book.getMedia_type(),
                book.getFormats(),
                book.getDownloadCount()
        );
    }

    // ----------------------
    // Listados
    // ----------------------
    public List<String> listarAutores() {
        return repository.findDistinctAutores();
    }

    @Transactional(readOnly = true)
    public List<Book> listarTodosConAutoresEIdiomas() {
        return repository.findAllWithAuthorsAndLanguages();
    }

    @Transactional(readOnly = true)
    public List<Book> listarLibrosPorIdioma(String idioma) {
        return repository.findByLanguagesContainingIgnoreCase(idioma);
    }

    @Transactional(readOnly = true)
    public void listarAutoresVivosEn(int year) {
        var autores = repository.findAutoresVivosEn(year); // lista de Person

        if (autores.isEmpty()) {
            System.out.println("❌ No se encontraron autores vivos en ese año.");
            return;
        }

        var todosLosLibros = listarTodosConAutoresEIdiomas(); // traemos todos los libros con autores

        for (var autor : autores) {
            System.out.println("Autor: " + autor.getName());
            System.out.println("Fecha de nacimiento: " + autor.getBirthYear());
            System.out.println("Fecha de fallecimiento: " + (autor.getDeathYear() != null ? autor.getDeathYear() : "Presente"));

            // obtener los libros de ese autor
            var librosAutor = todosLosLibros.stream()
                    .filter(b -> b.getAuthors() != null && b.getAuthors().contains(autor))
                    .map(Book::getTitle)
                    .toList();

            System.out.println("Libros: " + (librosAutor.isEmpty() ? "[]" : librosAutor));
            System.out.println("----------------------\n");
        }
    }


    // ----------------------
    // Impresiones formateadas
    // ----------------------
    @Transactional(readOnly = true)
    public void printAllBooksFormatted() {
        List<Book> books = listarTodosConAutoresEIdiomas();
        printBooksFormatted(books);
    }

    @Transactional(readOnly = true)
    public void printBooksByIdioma(String idioma) {
        List<Book> books = listarLibrosPorIdioma(idioma);
        printBooksFormatted(books);
    }

    @Transactional(readOnly = true)
    public void printAutoresConLibros() {
        var libros = listarTodosConAutoresEIdiomas();
        if (libros.isEmpty()) {
            System.out.println("❌ No hay libros para mostrar.");
            return;
        }

        Set<Person> autores = new HashSet<>();
        for (Book libro : libros) {
            if (libro.getAuthors() != null) {
                autores.addAll(libro.getAuthors());
            }
        }

        for (Person autor : autores) {
            System.out.println("Autor: " + autor.getName());
            System.out.println("Edad de nacimiento: " + autor.getBirthYear());
            System.out.println("Fecha de fallecimiento: " + autor.getDeathYear());
            System.out.print("Libros: ");
            List<String> titulos = libros.stream()
                    .filter(l -> l.getAuthors() != null && l.getAuthors().contains(autor))
                    .map(Book::getTitle)
                    .toList();
            System.out.println(titulos);
            System.out.println();
        }
    }


    // ----------------------
    // Método público para imprimir cualquier lista de libros
    // ----------------------
    @Transactional(readOnly = true)
    public void printBooksFormatted(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("❌ No hay libros para mostrar.");
            return;
        }
        for (Book book : books) {
            printBookInfo(book);
        }
    }

    // Método auxiliar privado para imprimir un libro individual
    private void printBookInfo(Book book) {
        String authors = (book.getAuthors() != null && !book.getAuthors().isEmpty())
                ? book.getAuthors().stream().map(a -> a.getName()).collect(Collectors.joining(", "))
                : "Desconocido";

        String langs = (book.getLanguages() != null && !book.getLanguages().isEmpty())
                ? String.join(", ", book.getLanguages())
                : "Desconocido";

        System.out.println("------ LIBRO ------");
        System.out.println("ID: " + book.getId());
        System.out.println("Título: " + book.getTitle());
        System.out.println("Autores: " + authors);
        System.out.println("Idiomas: " + langs);
        System.out.println("Número de descargas: " + book.getDownloadCount());
        System.out.println("------------------");
    }

    // EXTRAS del challenge
    public void printTop10LibrosMasDescargados() {
        List<Book> top10 = repository.findTop10ByOrderByDownloadCountDesc();

        if (top10.isEmpty()) {
            System.out.println("❌ No hay libros registrados.");
            return;
        }

        System.out.println("\n🔥 Top 10 libros más descargados:");
        int index = 1;
        for (Book book : top10) {
            System.out.printf("%2d. 📘 %s (Descargas: %d)\n",
                    index++,
                    book.getTitle(),
                    book.getDownloadCount());
        }

        System.out.println();
    }

    public void buscarAutorPorNombre(String nombre) {
        List<Book> libros = repository.findByAuthorNameContaining(nombre);

        if (libros.isEmpty()) {
            System.out.println("❌ No se encontraron autores con ese nombre.");
            return;
        }

        // Obtener autores reales sin repetir
        Set<Person> autoresEncontrados = new HashSet<>();
        libros.forEach(b -> autoresEncontrados.addAll(b.getAuthors()));

        System.out.println("\n👤 Autores encontrados:");
        autoresEncontrados.forEach(a -> System.out.println(" - " + a.getName()));

        System.out.println("\n📚 Libros asociados:");
        libros.forEach(b -> System.out.println(" - " + b.getTitle() + "\n"));
    }


    // ESTADISTICAS
    public void mostrarEstadisticas() {
        long totalLibros = repository.count();

        long totalAutores = repository.findAll().stream()
                .flatMap(b -> b.getAuthors().stream())
                .count();

        Book masDescargado = repository.findTop10ByOrderByDownloadCountDesc()
                .stream()
                .findFirst()
                .orElse(null);

        System.out.println("\n📊 ESTADÍSTICAS GENERALES");
        System.out.println("------------------------------");
        System.out.println("📚 Total de libros: " + totalLibros);
        System.out.println("👤 Total de autores: " + totalAutores);

        if (masDescargado != null) {
            System.out.println("🔥 Libro más descargado:");
            System.out.println("   " + masDescargado.getTitle() +
                    " (" + masDescargado.getDownloadCount() + " descargas) \n");
        }
    }

}

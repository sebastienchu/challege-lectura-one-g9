package com.alura.literatura.literatura.main;

import com.alura.literatura.literatura.model.Book;
import com.alura.literatura.literatura.service.BookService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private final Scanner teclado = new Scanner(System.in);
    private final BookService bookService;

    public Main(BookService bookService) {
        this.bookService = bookService;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    ===============================
                    📚 L I T E R A T U R A  A L U R A
                    ===============================
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Listar los 10 libros mas descargados
                    7 - Buscar autor por nombre
                    8 - Estadísticas
                    0 - Salir
                    ===============================
                    """);

            // Leer la opción como String y parsear a entero
            try {
                System.out.print("Ingrese una opción: ");
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
                continue; // vuelve al menú
            }

            switch (opcion) {

                case 1:
                    buscarLibro();
                    break;
                case 2:
                    bookService.printAllBooksFormatted();
                    break;
                case 3:
                    bookService.printAutoresConLibros();
                    break;

                case 4:
                    System.out.print("Ingrese el año: ");
                    int year = teclado.nextInt();
                    teclado.nextLine();
                    bookService.listarAutoresVivosEn(year);
                    break;
                case 5:
                    System.out.print("Ingrese el idioma (ej: en, es, fr): ");
                    String idioma = teclado.nextLine();
                    bookService.printBooksByIdioma(idioma);
                    break;
                case 6:
                    bookService.printTop10LibrosMasDescargados();
                    break;
                case 7:
                    System.out.print("Ingrese el nombre del autor: ");
                    String nombreAutor = teclado.nextLine();
                    bookService.buscarAutorPorNombre(nombreAutor);
                    break;
                case 8:
                    bookService.mostrarEstadisticas();
                    break;
                case 0:
                    System.out.println("👋 Saliendo...");
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Escribe el nombre del libro:");
        String nombre = teclado.nextLine();

        try {
            Book libro = bookService.buscarYGuardarComoEntidad(nombre);
            System.out.println("✔️ Libro guardado en BD:");
            bookService.printBooksFormatted(List.of(libro)); // <-- imprimimos como tarjeta
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

}

package com.alura.literatura.principal;

import java.util.Scanner;

public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/?t=";
//    private final ConsumoAPI consumoApi = new ConsumoAPI();
//    private final ConvierteDatos conversor = new ConvierteDatos();


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """

                    1 - Buscar Libro
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por tìtulo
                    5 - Top 5 Mejores series
                    6 - Buscar Series por Categoría
                    7 - Filtrar series
                    8 - Buscar episodios por nombre
                    9 - Top 5 eposidios por serie
                    ___________________________
                    0 - Salir

                    Ingrese una opcion.
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
//            case 2:
//                buscarEpisodioPorSerie();
//                break;
//            case 3:
//                mostrarSeriesBuscadas();
//                break;
//            case 4:
//                mostrarSeriesPorTitulo();
//                break;
//            case 5:
//                buscarTop5Series();
//                break;
//            case 6:
//                buscarSeriesPorCategoria();
//                break;
//            case 7:
//                filtrarSeriesPorTemporadaYEvaluacion();
//                break;
//            case 8:
//                buscarEpisodioPorTitulo();
//                break;
//            case 9:
//                buscarTop5Eposodios();
//                break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibro() {
        System.out.println("ACA no hago nada por ahora... ");
    }

    //  busca los datos generales del libro
//    private DatosLibro getDatosLibro() {
//        System.out.println("Escribe el nombre del libro que deseas buscar");
//        var nombreLibro = teclado.nextLine();
//        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
//        System.out.println(json);
//        DatosLibro datos = conversor.obtenerDatos(json, DatosLibros.class);
//        return datos;
//    }
}



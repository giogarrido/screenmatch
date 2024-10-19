package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.*;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import javax.swing.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e336056d";
    private ConvierteDatos conversor = new ConvierteDatos();
    private  List<DatosSerie> datosSeries = new ArrayList<>();

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar Series
                    2 - Buscar Episodios
                    3 - Mostrar Series Buscadas
               
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;

                case 2:
                    buscarEpisodioPorSerie();
                    break;

                case 3:
                    mostrarSeriesBuscadas();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;

                default:
                    System.out.println("Opción invalida");
            }
        }
    }// end muestraElMenu()



    private DatosSerie getDatosSerie() {
        System.out.println("Por favor escribe el nombre de la serie que desea buscar");
        //Busca los datos generales de las series
        var nombreSerie = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;

    } //end getDatosSerie()

    private void buscarEpisodioPorSerie() {
        DatosSerie datosSerie = getDatosSerie();
        List<DatosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {
            var json = consumoApi.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        datosSeries.add(datos);
        System.out.println(datos);
    }
    private void mostrarSeriesBuscadas() {
        List<Serie> series = new ArrayList<>();
        series = datosSeries.stream()
                .map(d -> new Serie(d))
                .collect(Collectors.toList());

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);



    }
}
//
//
//
//
//        List<DatosTemporadas> temporadas = new ArrayList<>();
//        for (int i = 1; i<=datos.totalDeTemporadas() ; i++){
//            json=consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ","+") + "&Season=" + i + API_KEY);
//            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
//            temporadas.add(datosTemporadas);
//
//        }
//        temporadas.forEach(System.out::println);
//
////        //mostrar solo el titulo de los episodios para las temporadas
////        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
////            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
////            for (int j = 0; j < episodiosTemporada.size(); j++) {
////                System.out.println(episodiosTemporada.get(j).titulo());
////            }
////
////        }
//
//        //Con expresiones lambda
//        System.out.println("Con lambda");
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//
//        //convetir todas las informaciones a una lista del tipo DatosEpisodio
//
//        List<DatosEpisodio> datosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                        .collect(Collectors.toList());
//
//        //top 5 episodios
//        System.out.println("Top 5 episodios");
//        datosEpisodios.stream()
//                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro N/A" + e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("Segundo ordenar (M>m)" + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("tercer filtro mayuscula " + e))
//                .limit(5)
//                .forEach(System.out::println);
//
//        //convirtiendo los datos a una lista del tipo episodio
//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(d -> new Episodio(t.numero(),d)))
//                .collect(Collectors.toList());
//        episodios.forEach(System.out::println);
////
////        //Busqueda de episodios a partir de x año
////        System.out.println("Por favor indica el año a partir del cual deseas ver los episodios");
////        var fecha = teclado.nextInt();
////        teclado.nextLine();
////
////        LocalDate fechaBusqueda = LocalDate.of(fecha, 1,1 );
////
////        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
////        episodios.stream()
////                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda) )
////                .forEach(e -> System.out.println(
////                        "Temporada " + e.getTemporada() +
////                                " Episodio " + e.getTitulo() +
////                                " Fecha de Lanzamiento" + e.getFechaDeLanzamiento().format(dtf)
////                ));
//
//        //Busca episodio por pedazo del titulo
//
////        System.out.println("Ingrese el titulo del episodio que desea ver");
////        var pedazoTitulo = teclado.nextLine();
////
////        Optional<Episodio> episodioBuscado = episodios.stream()
////                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
////                .findFirst();
////        if(episodioBuscado.isPresent()){
////            System.out.println("Episodio encontrado");
////            System.out.println("Los datos son: " + episodioBuscado.get());
////        }else {
////            System.out.println("Episodio no encontrado");
////        }
//
//        Map<Integer , Double > evaluacionesPorTemporada = episodios.stream()
//                .filter(e -> e.getEvaluacion() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getEvaluacion)));
//        System.out.println(evaluacionesPorTemporada);
//
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getEvaluacion() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
//        System.out.println(est);
//        System.out.println("Media de las evaluaciones: " + est.getAverage());
//        System.out.println("Episodio mejor evaluado: " + est.getMax());
//        System.out.println("Episodio peor evaluado: " + est.getMin());
//
//
//}

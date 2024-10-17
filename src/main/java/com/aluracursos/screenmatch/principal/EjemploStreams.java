package com.aluracursos.screenmatch.principal;

import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList("Giovanni","Raul","Maria","Carlos","Genesys");

        nombres.stream()
                .sorted()
                .limit(5)
                .filter(n -> n.startsWith("G"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);

    }
}

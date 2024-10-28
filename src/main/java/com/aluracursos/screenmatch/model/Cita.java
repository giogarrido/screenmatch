package com.aluracursos.screenmatch.model;

import jakarta.persistence.*;


@Entity
@Table(name = "citas")
public class Cita {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String frase;
    private String personaje;
    @ManyToOne
    private Serie serie;

    public Cita() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFrase() {
        return frase;
    }

    public void setFrase(String cita) {
        this.frase = cita;
    }

    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String citadoPor) {
        this.personaje = citadoPor;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return
                " cita='" + frase + '\'' +
                ", citadoPor='" + personaje + '\'' +
                ", titulo='" + serie.getTitulo() + '\'' ;
    }
}


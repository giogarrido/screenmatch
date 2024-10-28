package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.dto.CitaDTO;
import com.aluracursos.screenmatch.model.Cita;
import com.aluracursos.screenmatch.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CitaService {
    @Autowired
    private CitaRepository citaRepository;

    public CitaDTO obtenerCitaRandom() {
//        CitaDTO cita = new CitaDTO("https://m.media-amazon.com/images/M/MV5BOGY3NTg1ODMtOGIzZS00YWFiLTgzYzktNzBiYWZkYjcwNGRhXkEyXkFqcGc@._V1_SX300.jpg","sabeTitulo","a la grande le puse cuca","Homerenchion");
//        return cita;
        Optional<Cita> cita = citaRepository.findRandomCita();
        System.out.println(cita);
        if(cita.isPresent()){
//            Cita c = cita.get();
//            System.out.println(c);
//            return new CitaDTO(c.getSerie().getPoster(),c.getSerie().getTitulo(),c.getFrase(),c.getPersonaje());
            CitaDTO cita2 = new CitaDTO("https://m.media-amazon.com/images/M/MV5BOGY3NTg1ODMtOGIzZS00YWFiLTgzYzktNzBiYWZkYjcwNGRhXkEyXkFqcGc@._V1_SX300.jpg","sabeTitulo","a la grande le puse cuca","Homerenchion");
            return cita2;
        }
        return null;
    }
}

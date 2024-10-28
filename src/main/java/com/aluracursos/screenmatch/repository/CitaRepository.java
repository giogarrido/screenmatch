package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    @Query(value = "SELECT * FROM citas ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<com.aluracursos.screenmatch.model.Cita> findRandomCita();
}

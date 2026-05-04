package com.bienestar.repository;

import com.bienestar.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByProfesional_IdAndActivoTrue(Long profesionalId);
}

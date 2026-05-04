package com.bienestar.repository;

import com.bienestar.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByUsuario_Id(Long usuarioId);
    Optional<Estudiante> findByUsuario_Correo(String correo);
    boolean existsByCodigo(String codigo);
}

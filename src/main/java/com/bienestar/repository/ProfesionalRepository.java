package com.bienestar.repository;

import com.bienestar.model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

    // Método clave para buscar al profesional a través del ID de su cuenta de usuario
    Optional<Profesional> findByUsuario_Id(Long usuarioId);

    Optional<Profesional> findByUsuario_Correo(String correo);
}
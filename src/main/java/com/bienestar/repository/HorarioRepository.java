package com.bienestar.repository;

import com.bienestar.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {

    // 🎯 LA SOLUCIÓN: Buscar horarios donde el profesional tenga este ID de Usuario
    List<Horario> findByProfesional_Usuario_Id(Long usuarioId);

    // Si usas este para el administrador, está bien que se quede
    List<Horario> findByProfesional_Id(Long profesionalId);
}
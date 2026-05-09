package com.bienestar.repository;

import com.bienestar.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    // 🎯 ESTE ES EL QUE HACE LA MAGIA
    // Busca solicitudes -> profesional -> usuario -> id
    List<Solicitud> findByProfesional_Usuario_Id(Long usuarioId);

    List<Solicitud> findByEstudiante_Id(Long estudianteId);

    @Query("SELECT s.tipo, COUNT(DISTINCT s.estudiante.id) FROM Solicitud s GROUP BY s.tipo")
    List<Object[]> countEstudiantesPorTipo();
}
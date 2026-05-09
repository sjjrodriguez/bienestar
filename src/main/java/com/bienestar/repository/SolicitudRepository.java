package com.bienestar.repository;

import com.bienestar.model.Solicitud;
import com.bienestar.model.EstadoSolicitud;
import com.bienestar.model.TipoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    // 1. Lo que ya tenías (¡Muy bien!)
    List<Solicitud> findByProfesional_Usuario_Id(Long usuarioId);
    List<Solicitud> findByEstudiante_Id(Long estudianteId);

    // 2. BUSCAR POR ESTADO (Vital para el tablero de control)
    // Ejemplo: Ver solo las 'PENDIENTES'
    List<Solicitud> findByEstado(EstadoSolicitud estado);

    // 3. BUSCAR POR TIPO (Para separar Apoyo Académico de Psicología)
    List<Solicitud> findByTipo(TipoSolicitud tipo);

    // 4. SOLICITUDES SIN PROFESIONAL (Para que el Admin las asigne)
    List<Solicitud> findByProfesionalIsNull();

    // 5. TU QUERY DE ESTADÍSTICAS (La magia para los reportes)
    @Query("SELECT s.tipo, COUNT(DISTINCT s.estudiante.id) FROM Solicitud s GROUP BY s.tipo")
    List<Object[]> countEstudiantesPorTipo();
}
package com.bienestar.repository;

import com.bienestar.model.Cita;
import com.bienestar.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByEstudiante_Id(Long estudianteId);
    List<Cita> findByProfesional_Id(Long profesionalId);
    List<Cita> findByProfesional_IdAndFecha(Long profesionalId, LocalDate fecha);
    boolean existsByHorario_IdAndFechaAndEstadoNot(Long horarioId, LocalDate fecha, EstadoCita estado);

    @Query("SELECT c.estado, COUNT(c) FROM Cita c GROUP BY c.estado")
    List<Object[]> countPorEstado();
}

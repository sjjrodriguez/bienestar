package com.bienestar.repository;

import com.bienestar.model.Cita;
import com.bienestar.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    // ─── CONSULTAS PARA EL ESTUDIANTE ──────────────────────────
    List<Cita> findByEstudiante_Id(Long estudianteId);

    // ─── CONSULTAS PARA EL PROFESIONAL (Basadas en ID de Usuario) ─

    // 🎯 ESTOS SON LOS QUE TE HACÍAN FALTA:
    // Permiten cruzar desde Cita -> Profesional -> Usuario -> Id
    List<Cita> findByProfesional_Usuario_Id(Long usuarioId);

    List<Cita> findByProfesional_Usuario_IdAndFecha(Long usuarioId, LocalDate fecha);

    // ─── VALIDACIONES Y ESTADÍSTICAS ────────────────────────────

    boolean existsByHorario_IdAndFechaAndEstadoNot(Long horarioId, LocalDate fecha, EstadoCita estado);

    @Query("SELECT c.estado, COUNT(c) FROM Cita c GROUP BY c.estado")
    List<Object[]> countPorEstado();
}
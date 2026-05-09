package com.bienestar.repository;

import com.bienestar.model.Cita;
import com.bienestar.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    // ─── CONSULTAS PARA EL ESTUDIANTE (CON EL PUENTE AL USUARIO) ───

    // 🎯 ESTA ES LA SOLUCIÓN: Busca la cita por el ID de Usuario del estudiante
    List<Cita> findByEstudiante_Usuario_Id(Long usuarioId);

    // ─── CONSULTAS PARA EL PROFESIONAL ──────────────────────────
    List<Cita> findByProfesional_Usuario_Id(Long usuarioId);
    List<Cita> findByProfesional_Usuario_IdAndFecha(Long usuarioId, LocalDate fecha);

    // ─── VALIDACIONES ───────────────────────────────────────────
    boolean existsByHorario_IdAndFechaAndEstadoNot(Long horarioId, LocalDate fecha, EstadoCita estado);

    @Query("SELECT c.estado, COUNT(c) FROM Cita c GROUP BY c.estado")
    List<Object[]> countPorEstado();
}
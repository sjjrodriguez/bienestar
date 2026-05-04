package com.bienestar.repository;

import com.bienestar.model.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
    List<Seguimiento> findBySolicitud_Id(Long solicitudId);
    List<Seguimiento> findByProfesional_Id(Long profesionalId);
    List<Seguimiento> findBySolicitud_Estudiante_Id(Long estudianteId);
}

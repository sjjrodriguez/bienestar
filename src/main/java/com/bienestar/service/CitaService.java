package com.bienestar.service;

import com.bienestar.dto.CitaDTO;
import java.time.LocalDate;
import java.util.List;

public interface CitaService {
    CitaDTO.Response agendar(Long estudianteId, CitaDTO.Request request);
    List<CitaDTO.Response> listarPorEstudiante(Long estudianteId);
    List<CitaDTO.Response> listarPorProfesional(Long profesionalId);
    List<CitaDTO.Response> listarPorProfesionalYFecha(Long profesionalId, LocalDate fecha);
    CitaDTO.Response actualizarEstado(Long citaId, CitaDTO.ActualizarEstadoRequest request);

    // 🎯 NUEVO MÉTODO PARA EL ADMINISTRADOR
    List<CitaDTO.Response> listarTodos();
}
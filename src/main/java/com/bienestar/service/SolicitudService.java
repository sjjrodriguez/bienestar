package com.bienestar.service;

import com.bienestar.dto.SolicitudDTO;
import java.util.List;

public interface SolicitudService {
    SolicitudDTO.Response crear(Long estudianteId, SolicitudDTO.Request request);
    List<SolicitudDTO.Response> listarPorEstudiante(Long estudianteId);
    List<SolicitudDTO.Response> listarTodas();
    SolicitudDTO.Response actualizarEstado(Long id, SolicitudDTO.ActualizarEstadoRequest request);
}

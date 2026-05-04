package com.bienestar.service;

import com.bienestar.dto.SeguimientoDTO;
import java.util.List;

public interface SeguimientoService {
    SeguimientoDTO.Response registrar(Long profesionalId, SeguimientoDTO.Request request);
    List<SeguimientoDTO.Response> listarPorSolicitud(Long solicitudId);
    List<SeguimientoDTO.Response> listarPorEstudiante(Long estudianteId);
}

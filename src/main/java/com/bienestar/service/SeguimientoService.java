package com.bienestar.service;

import com.bienestar.dto.SeguimientoDTO;
import java.util.List;

public interface SeguimientoService {

    // 1. Método para guardar en Supabase (Ahora coincide con el Impl y el Controller)
    SeguimientoDTO.Response crear(SeguimientoDTO.Request request);

    // 2. Método para ver el historial
    List<SeguimientoDTO.Response> listarPorSolicitud(Long solicitudId);
}
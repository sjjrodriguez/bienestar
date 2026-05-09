package com.bienestar.service;

import com.bienestar.dto.HorarioDTO;
import java.util.List;

public interface HorarioService {
    // Para el Admin
    HorarioDTO.Response crear(HorarioDTO.Request request);

    // 🎯 NUEVO: Para el Profesional (Usa su ID de usuario de la sesión)
    HorarioDTO.Response crearDesdeProfesional(Long usuarioId, HorarioDTO.Request request);

    List<HorarioDTO.Response> listarPorProfesional(Long profesionalId);
    List<HorarioDTO.Response> listarPorUsuario(Long usuarioId);

    void desactivar(Long id);
}
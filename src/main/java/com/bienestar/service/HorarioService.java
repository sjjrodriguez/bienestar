package com.bienestar.service;

import com.bienestar.dto.HorarioDTO;
import java.util.List;

public interface HorarioService {
    HorarioDTO.Response crear(HorarioDTO.Request request);

    // 🎯 Para el Admin (Usa profesionalId de la tabla profesionales)
    List<HorarioDTO.Response> listarPorProfesional(Long profesionalId);

    // 🎯 Para el Profesional logueado (Usa usuarioId de la tabla usuarios)
    List<HorarioDTO.Response> listarPorUsuario(Long usuarioId);

    void desactivar(Long id);
}
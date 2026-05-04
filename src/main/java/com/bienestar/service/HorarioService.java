package com.bienestar.service;

import com.bienestar.dto.HorarioDTO;
import java.util.List;

public interface HorarioService {
    HorarioDTO.Response crear(HorarioDTO.Request request);
    List<HorarioDTO.Response> listarPorProfesional(Long profesionalId);
    void desactivar(Long id);
}

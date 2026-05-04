package com.bienestar.service;

import com.bienestar.dto.EstudianteDTO;
import java.util.List;

public interface EstudianteService {
    EstudianteDTO.Response crear(EstudianteDTO.Request request);
    List<EstudianteDTO.Response> listarTodos();
    EstudianteDTO.Response buscarPorId(Long id);
    void desactivar(Long id);
}

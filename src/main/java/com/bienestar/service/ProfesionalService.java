package com.bienestar.service;

import com.bienestar.dto.ProfesionalDTO;
import java.util.List;

public interface ProfesionalService {
    ProfesionalDTO.Response crear(ProfesionalDTO.Request request);
    List<ProfesionalDTO.Response> listarTodos();
}

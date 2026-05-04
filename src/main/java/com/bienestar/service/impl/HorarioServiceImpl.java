package com.bienestar.service.impl;

import com.bienestar.dto.HorarioDTO;
import com.bienestar.model.Horario;
import com.bienestar.model.Profesional;
import com.bienestar.repository.HorarioRepository;
import com.bienestar.repository.ProfesionalRepository;
import com.bienestar.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final ProfesionalRepository profesionalRepository;

    @Override
    @Transactional
    public HorarioDTO.Response crear(HorarioDTO.Request request) {
        Profesional profesional = profesionalRepository.findById(request.getProfesionalId())
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        Horario horario = Horario.builder()
                .profesional(profesional)
                .dia(request.getDia())
                .horaInicio(request.getHoraInicio())
                .horaFin(request.getHoraFin())
                .build();

        return toResponse(horarioRepository.save(horario));
    }

    @Override
    public List<HorarioDTO.Response> listarPorProfesional(Long profesionalId) {
        return horarioRepository.findByProfesional_IdAndActivoTrue(profesionalId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void desactivar(Long id) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        horario.setActivo(false);
        horarioRepository.save(horario);
    }

    private HorarioDTO.Response toResponse(Horario h) {
        return HorarioDTO.Response.builder()
                .id(h.getId())
                .profesionalId(h.getProfesional().getId())
                .nombreProfesional(h.getProfesional().getUsuario().getNombre())
                .dia(h.getDia())
                .horaInicio(h.getHoraInicio())
                .horaFin(h.getHoraFin())
                .activo(h.isActivo())
                .build();
    }
}

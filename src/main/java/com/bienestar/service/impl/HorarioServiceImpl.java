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

    // Se mantiene para el Admin
    @Override
    public HorarioDTO.Response crear(HorarioDTO.Request request) {
        Profesional prof = profesionalRepository.findById(request.getProfesionalId())
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));
        return guardarHorario(prof, request);
    }

    // 🎯 SOLUCIÓN PARA EL PROFESIONAL: Busca por el ID de Usuario
    @Override
    @Transactional
    public HorarioDTO.Response crearDesdeProfesional(Long usuarioId, HorarioDTO.Request request) {
        Profesional prof = profesionalRepository.findByUsuario_Id(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se encontró el perfil profesional para este usuario"));

        return guardarHorario(prof, request);
    }

    // Método privado para no repetir código de guardado
    private HorarioDTO.Response guardarHorario(Profesional prof, HorarioDTO.Request request) {
        Horario horario = Horario.builder()
                .profesional(prof)
                .dia(request.getDia())
                .horaInicio(request.getHoraInicio())
                .horaFin(request.getHoraFin())
                .activo(true)
                .build();
        return toResponse(horarioRepository.save(horario));
    }

    @Override
    public List<HorarioDTO.Response> listarPorProfesional(Long profesionalId) {
        return horarioRepository.findByProfesional_Id(profesionalId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<HorarioDTO.Response> listarPorUsuario(Long usuarioId) {
        return horarioRepository.findByProfesional_Usuario_Id(usuarioId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public void deactivate(Long id) {
        Horario h = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        h.setActivo(false);
        horarioRepository.save(h);
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
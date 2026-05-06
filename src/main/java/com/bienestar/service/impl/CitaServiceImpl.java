package com.bienestar.service.impl;

import com.bienestar.dto.CitaDTO;
import com.bienestar.model.*;
import com.bienestar.repository.*;
import com.bienestar.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final EstudianteRepository estudianteRepository;
    private final ProfesionalRepository profesionalRepository;
    private final HorarioRepository horarioRepository;

    @Override
    @Transactional
    public CitaDTO.Response agendar(Long estudianteId, CitaDTO.Request request) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Profesional profesional = profesionalRepository.findById(request.getProfesionalId())
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        Horario horario = horarioRepository.findById(request.getHorarioId())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        boolean ocupado = citaRepository.existsByHorario_IdAndFechaAndEstadoNot(
                horario.getId(), request.getFecha(), EstadoCita.CANCELADA);
        if (ocupado) {
            throw new RuntimeException("El horario ya está ocupado para esa fecha");
        }

        Cita cita = Cita.builder()
                .estudiante(estudiante)
                .profesional(profesional)
                .horario(horario)
                .fecha(request.getFecha())
                .motivo(request.getMotivo())
                .build();

        return toResponse(citaRepository.save(cita));
    }

    @Override
    public List<CitaDTO.Response> listarPorEstudiante(Long estudianteId) {
        return citaRepository.findByEstudiante_Id(estudianteId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO.Response> listarPorProfesional(Long profesionalId) {
        return citaRepository.findByProfesional_Id(profesionalId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO.Response> listarPorProfesionalYFecha(Long profesionalId, LocalDate fecha) {
        return citaRepository.findByProfesional_IdAndFecha(profesionalId, fecha).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CitaDTO.Response actualizarEstado(Long citaId, CitaDTO.ActualizarEstadoRequest request) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setEstado(request.getEstado());
        if (request.getNotaProfesional() != null) {
            cita.setNotaProfesional(request.getNotaProfesional());
        }
        return toResponse(citaRepository.save(cita));
    }

    private CitaDTO.Response toResponse(Cita c) {
        return CitaDTO.Response.builder()
                .id(c.getId())
                .estudianteId(c.getEstudiante().getId())
                .nombreEstudiante(c.getEstudiante().getUsuario().getNombre())
                .profesionalId(c.getProfesional().getId())
                .nombreProfesional(c.getProfesional().getUsuario().getNombre())
                .especialidad(c.getProfesional().getEspecialidad()) // <--- MAPEO NUEVO
                .horarioId(c.getHorario().getId())
                .dia(c.getHorario().getDia())
                .horaInicio(c.getHorario().getHoraInicio().toString())
                .fecha(c.getFecha())
                .estado(c.getEstado())
                .motivo(c.getMotivo())
                .notaProfesional(c.getNotaProfesional())
                .createdAt(c.getCreatedAt())
                .build();
    }
}

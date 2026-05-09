package com.bienestar.service.impl;

import com.bienestar.dto.SolicitudDTO;
import com.bienestar.model.*;
import com.bienestar.repository.*;
import com.bienestar.service.SolicitudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final EstudianteRepository estudianteRepository;

    @Override
    @Transactional
    public SolicitudDTO.Response crear(Long estudianteId, SolicitudDTO.Request request) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Solicitud solicitud = Solicitud.builder()
                .estudiante(estudiante)
                .tipo(request.getTipo())
                .descripcion(request.getDescripcion()) // Hibernate lo mapea a 'motivo' en la DB
                .estado(EstadoSolicitud.PENDIENTE)
                .build();

        return toResponse(solicitudRepository.save(solicitud));
    }

    @Override
    public List<SolicitudDTO.Response> listarPorEstudiante(Long estudianteId) {
        return solicitudRepository.findByEstudiante_Id(estudianteId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<SolicitudDTO.Response> listarTodas() {
        return solicitudRepository.findAll().stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SolicitudDTO.Response actualizarEstado(Long id, SolicitudDTO.ActualizarEstadoRequest request) {
        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(request.getEstado());
        // Aquí podrías asignar el profesional que está actualizando la solicitud si fuera necesario

        return toResponse(solicitudRepository.save(solicitud));
    }

    private SolicitudDTO.Response toResponse(Solicitud s) {
        return SolicitudDTO.Response.builder()
                .id(s.getId())
                .estudianteId(s.getEstudiante().getId())
                .nombreEstudiante(s.getEstudiante().getUsuario().getNombre())
                .tipo(s.getTipo())
                .descripcion(s.getDescripcion())
                .estado(s.getEstado())
                .createdAt(s.getCreatedAt())
                // Si la solicitud ya tiene un profesional asignado, mandamos su nombre
                .nombreProfesional(s.getProfesional() != null ? s.getProfesional().getUsuario().getNombre() : "Pendiente de asignar")
                .build();
    }
}
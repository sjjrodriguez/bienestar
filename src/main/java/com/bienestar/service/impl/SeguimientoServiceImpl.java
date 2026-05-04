package com.bienestar.service.impl;

import com.bienestar.dto.SeguimientoDTO;
import com.bienestar.model.Profesional;
import com.bienestar.model.Seguimiento;
import com.bienestar.model.Solicitud;
import com.bienestar.repository.ProfesionalRepository;
import com.bienestar.repository.SeguimientoRepository;
import com.bienestar.repository.SolicitudRepository;
import com.bienestar.service.SeguimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeguimientoServiceImpl implements SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;
    private final SolicitudRepository solicitudRepository;
    private final ProfesionalRepository profesionalRepository;

    @Override
    @Transactional
    public SeguimientoDTO.Response registrar(Long profesionalId, SeguimientoDTO.Request request) {
        Solicitud solicitud = solicitudRepository.findById(request.getSolicitudId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Profesional profesional = profesionalRepository.findById(profesionalId)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        Seguimiento seguimiento = Seguimiento.builder()
                .solicitud(solicitud)
                .profesional(profesional)
                .nota(request.getNota())
                .build();

        return toResponse(seguimientoRepository.save(seguimiento));
    }

    @Override
    public List<SeguimientoDTO.Response> listarPorSolicitud(Long solicitudId) {
        return seguimientoRepository.findBySolicitud_Id(solicitudId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<SeguimientoDTO.Response> listarPorEstudiante(Long estudianteId) {
        return seguimientoRepository.findBySolicitud_Estudiante_Id(estudianteId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    private SeguimientoDTO.Response toResponse(Seguimiento s) {
        return SeguimientoDTO.Response.builder()
                .id(s.getId())
                .solicitudId(s.getSolicitud().getId())
                .profesionalId(s.getProfesional().getId())
                .nombreProfesional(s.getProfesional().getUsuario().getNombre())
                .nota(s.getNota())
                .fechaRegistro(s.getFechaRegistro())
                .build();
    }
}

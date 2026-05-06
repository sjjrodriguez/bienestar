package com.bienestar.service.impl;

import com.bienestar.dto.SeguimientoDTO;
import com.bienestar.model.Profesional;
import com.bienestar.model.Seguimiento;
import com.bienestar.model.Solicitud;
import com.bienestar.repository.ProfesionalRepository;
import com.bienestar.repository.SeguimientoRepository;
import com.bienestar.repository.SolicitudRepository;
import com.bienestar.service.SeguimientoService; // Interfaz que debes tener creada
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeguimientoServiceImpl implements SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;
    private final ProfesionalRepository profesionalRepository;
    private final SolicitudRepository solicitudRepository;

    @Override
    @Transactional
    public SeguimientoDTO.Response crear(SeguimientoDTO.Request request) {

        // 1. Buscamos que existan el profesional y la solicitud
        Profesional profesional = profesionalRepository.findById(request.getProfesionalId())
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        Solicitud solicitud = solicitudRepository.findById(request.getSolicitudId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // 2. Armamos la entidad para Supabase
        Seguimiento seguimiento = Seguimiento.builder()
                .nota(request.getNota())
                .profesional(profesional)
                .solicitud(solicitud)
                // fechaRegistro se genera sola por el @Builder.Default de la entidad
                .build();

        // 3. Guardamos
        seguimiento = seguimientoRepository.save(seguimiento);

        // Opcional: Aquí podrías cambiar el estado de la Solicitud a "PROCESADA" o "ATENDIDA"
        // solicitud.setEstado(EstadoSolicitud.PROCESADA);
        // solicitudRepository.save(solicitud);

        return toResponse(seguimiento);
    }

    @Override
    public List<SeguimientoDTO.Response> listarPorSolicitud(Long solicitudId) {
        return seguimientoRepository.findBySolicitud_Id(solicitudId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    // Convertidor de Entidad a DTO
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
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

import java.util.Collections;
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

        // 👇 ESTO NOS DIRÁ LA VERDAD EN LOS LOGS DE RENDER 👇
        System.out.println("[DEBUG] Recibiendo solicitudId: " + request.getSolicitudId());
        System.out.println("[DEBUG] Recibiendo profesionalId (ID de Usuario): " + request.getProfesionalId());

        // 1. Buscamos al profesional usando el ID de Usuario que viene de Android
        Profesional profesional = profesionalRepository.findByUsuario_Id(request.getProfesionalId())
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado para el usuario ID: " + request.getProfesionalId()));

        // 2. Buscamos la solicitud (Si falla aquí, es por el JOIN con Estudiante/Usuario)
        Solicitud solicitud = solicitudRepository.findById(request.getSolicitudId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + request.getSolicitudId()));

        // 3. Armamos la entidad para Supabase
        Seguimiento seguimiento = Seguimiento.builder()
                .nota(request.getNota())
                .profesional(profesional)
                .solicitud(solicitud)
                .build();

        // 4. Guardamos
        seguimiento = seguimientoRepository.save(seguimiento);
        System.out.println("[DEBUG] ¡Seguimiento guardado exitosamente con ID: " + seguimiento.getId() + "!");

        return toResponse(seguimiento);
    }

    @Override
    public List<SeguimientoDTO.Response> listarPorSolicitud(Long solicitudId) {
        return seguimientoRepository.findBySolicitud_Id(solicitudId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SeguimientoDTO.Response registrar(Long profesionalId, SeguimientoDTO.Request request) {
        request.setProfesionalId(profesionalId);
        return crear(request);
    }

    @Override
    public List<SeguimientoDTO.Response> listarPorEstudiante(Long estudianteId) {
        return Collections.emptyList();
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
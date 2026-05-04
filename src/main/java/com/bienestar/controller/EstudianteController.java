package com.bienestar.controller;

import com.bienestar.dto.CitaDTO;
import com.bienestar.dto.SeguimientoDTO;
import com.bienestar.dto.SolicitudDTO;
import com.bienestar.service.CitaService;
import com.bienestar.service.SeguimientoService;
import com.bienestar.service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiante")
@RequiredArgsConstructor
public class EstudianteController {

    private final SolicitudService solicitudService;
    private final CitaService citaService;
    private final SeguimientoService seguimientoService;

    // ── Solicitudes ───────────────────────────────────────────
    @PostMapping("/{estudianteId}/solicitudes")
    public ResponseEntity<SolicitudDTO.Response> crearSolicitud(
            @PathVariable Long estudianteId,
            @Valid @RequestBody SolicitudDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(solicitudService.crear(estudianteId, request));
    }

    @GetMapping("/{estudianteId}/solicitudes")
    public ResponseEntity<List<SolicitudDTO.Response>> misSolicitudes(
            @PathVariable Long estudianteId) {
        return ResponseEntity.ok(solicitudService.listarPorEstudiante(estudianteId));
    }

    // ── Citas ─────────────────────────────────────────────────
    @PostMapping("/{estudianteId}/citas")
    public ResponseEntity<CitaDTO.Response> agendarCita(
            @PathVariable Long estudianteId,
            @Valid @RequestBody CitaDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(citaService.agendar(estudianteId, request));
    }

    @GetMapping("/{estudianteId}/citas")
    public ResponseEntity<List<CitaDTO.Response>> misCitas(
            @PathVariable Long estudianteId) {
        return ResponseEntity.ok(citaService.listarPorEstudiante(estudianteId));
    }

    // ── Seguimientos ──────────────────────────────────────────
    @GetMapping("/{estudianteId}/seguimientos")
    public ResponseEntity<List<SeguimientoDTO.Response>> misSeguimientos(
            @PathVariable Long estudianteId) {
        return ResponseEntity.ok(seguimientoService.listarPorEstudiante(estudianteId));
    }
}

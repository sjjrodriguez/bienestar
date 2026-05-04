package com.bienestar.controller;

import com.bienestar.dto.CitaDTO;
import com.bienestar.dto.SeguimientoDTO;
import com.bienestar.dto.SolicitudDTO;
import com.bienestar.service.CitaService;
import com.bienestar.service.SeguimientoService;
import com.bienestar.service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/profesional")
@RequiredArgsConstructor
public class ProfesionalController {

    private final CitaService citaService;
    private final SeguimientoService seguimientoService;
    private final SolicitudService solicitudService;

    // ── Citas ─────────────────────────────────────────────────
    @GetMapping("/{profesionalId}/citas")
    public ResponseEntity<List<CitaDTO.Response>> todasMisCitas(
            @PathVariable Long profesionalId) {
        return ResponseEntity.ok(citaService.listarPorProfesional(profesionalId));
    }

    @GetMapping("/{profesionalId}/citas/dia")
    public ResponseEntity<List<CitaDTO.Response>> citasDelDia(
            @PathVariable Long profesionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(citaService.listarPorProfesionalYFecha(profesionalId, fecha));
    }

    @PatchMapping("/citas/{citaId}/estado")
    public ResponseEntity<CitaDTO.Response> registrarAsistencia(
            @PathVariable Long citaId,
            @Valid @RequestBody CitaDTO.ActualizarEstadoRequest request) {
        return ResponseEntity.ok(citaService.actualizarEstado(citaId, request));
    }

    // ── Seguimientos ──────────────────────────────────────────
    @PostMapping("/{profesionalId}/seguimientos")
    public ResponseEntity<SeguimientoDTO.Response> registrarSeguimiento(
            @PathVariable Long profesionalId,
            @Valid @RequestBody SeguimientoDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seguimientoService.registrar(profesionalId, request));
    }

    @GetMapping("/seguimientos/solicitud/{solicitudId}")
    public ResponseEntity<List<SeguimientoDTO.Response>> seguimientosPorSolicitud(
            @PathVariable Long solicitudId) {
        return ResponseEntity.ok(seguimientoService.listarPorSolicitud(solicitudId));
    }

    @GetMapping("/seguimientos/estudiante/{estudianteId}")
    public ResponseEntity<List<SeguimientoDTO.Response>> seguimientosPorEstudiante(
            @PathVariable Long estudianteId) {
        return ResponseEntity.ok(seguimientoService.listarPorEstudiante(estudianteId));
    }

    // ── Casos activos ─────────────────────────────────────────
    @GetMapping("/casos")
    public ResponseEntity<List<SolicitudDTO.Response>> casosActivos() {
        return ResponseEntity.ok(
                solicitudService.listarTodas().stream()
                        .filter(s -> s.getEstado().name().equals("EN_PROCESO"))
                        .toList()
        );
    }

    @PatchMapping("/solicitudes/{solicitudId}/estado")
    public ResponseEntity<SolicitudDTO.Response> actualizarEstadoSolicitud(
            @PathVariable Long solicitudId,
            @Valid @RequestBody SolicitudDTO.ActualizarEstadoRequest request) {
        return ResponseEntity.ok(solicitudService.actualizarEstado(solicitudId, request));
    }
}

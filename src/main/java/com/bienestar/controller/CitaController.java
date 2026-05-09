package com.bienestar.controller;

import com.bienestar.dto.CitaDTO;
import com.bienestar.service.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    // 1. Agendar Cita (POST) - AHORA RECIBE EL ID DEL ESTUDIANTE
    // Ruta: POST http://localhost:8080/api/citas/estudiante/{estudianteId}
    @PostMapping("/estudiante/{estudianteId}")
    public ResponseEntity<CitaDTO.Response> agendarCita(
            @PathVariable Long estudianteId,
            @Valid @RequestBody CitaDTO.Request request) {
        // Long estudianteIdFijo = 1L; // <- BORRAMOS ESTO
        CitaDTO.Response response = citaService.agendar(estudianteId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Listar todas las citas del estudiante (GET) - AHORA RECIBE EL ID
    // Ruta: GET http://localhost:8080/api/citas/estudiante/{estudianteId}
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<CitaDTO.Response>> listarPorEstudiante(@PathVariable Long estudianteId) {
        // Long estudianteIdFijo = 1L; // <- BORRAMOS ESTO
        List<CitaDTO.Response> responses = citaService.listarPorEstudiante(estudianteId);
        return ResponseEntity.ok(responses);
    }

    // 3. Listar citas por profesional
    // Ruta: GET http://localhost:8080/api/citas/profesional/{usuarioId}
    @GetMapping("/profesional/{usuarioId}")
    public ResponseEntity<List<CitaDTO.Response>> listarPorProfesional(@PathVariable Long usuarioId) {
        // 🎯 IMPORTANTE: El Service ya tiene el método findByProfesional_Usuario_Id
        List<CitaDTO.Response> responses = citaService.listarPorProfesional(usuarioId);
        return ResponseEntity.ok(responses);
    }

    // 4. Listar citas por profesional y fecha (GET)
    // Ruta: GET http://localhost:8080/api/citas/profesional/2/fecha/2026-05-15
    @GetMapping("/profesional/{profesionalId}/fecha/{fecha}")
    public ResponseEntity<List<CitaDTO.Response>> listarPorProfesionalYFecha(
            @PathVariable Long profesionalId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<CitaDTO.Response> responses = citaService.listarPorProfesionalYFecha(profesionalId, fecha);
        return ResponseEntity.ok(responses);
    }

    // 5. Actualizar estado de la cita y/o nota del profesional (PUT)
    // Ruta: PUT http://localhost:8080/api/citas/10/estado
    @PutMapping("/{citaId}/estado")
    public ResponseEntity<CitaDTO.Response> actualizarEstado(
            @PathVariable Long citaId,
            @Valid @RequestBody CitaDTO.ActualizarEstadoRequest request) {

        CitaDTO.Response response = citaService.actualizarEstado(citaId, request);
        return ResponseEntity.ok(response);
    }
}
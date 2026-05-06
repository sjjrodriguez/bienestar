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

    // 1. Agendar Cita (POST)
    // Ruta: POST http://localhost:8080/api/citas
    @PostMapping
    public ResponseEntity<CitaDTO.Response> agendarCita(@Valid @RequestBody CitaDTO.Request request) {
        Long estudianteIdFijo = 1L; // ID fijo para el estudiante
        CitaDTO.Response response = citaService.agendar(estudianteIdFijo, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Listar todas las citas del estudiante (GET)
    // Ruta: GET http://localhost:8080/api/citas/estudiante
    @GetMapping("/estudiante")
    public ResponseEntity<List<CitaDTO.Response>> listarPorEstudiante() {
        Long estudianteIdFijo = 1L; // Reutilizamos el ID fijo
        List<CitaDTO.Response> responses = citaService.listarPorEstudiante(estudianteIdFijo);
        return ResponseEntity.ok(responses);
    }

    // 3. Listar citas por profesional (GET)
    // Ruta: GET http://localhost:8080/api/citas/profesional/2
    @GetMapping("/profesional/{profesionalId}")
    public ResponseEntity<List<CitaDTO.Response>> listarPorProfesional(@PathVariable Long profesionalId) {
        List<CitaDTO.Response> responses = citaService.listarPorProfesional(profesionalId);
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
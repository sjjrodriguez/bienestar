package com.bienestar.controller;

import com.bienestar.dto.EstudianteDTO;
import com.bienestar.dto.HorarioDTO;
import com.bienestar.dto.ProfesionalDTO;
import com.bienestar.dto.ReporteDTO;
import com.bienestar.service.EstudianteService;
import com.bienestar.service.HorarioService;
import com.bienestar.service.ProfesionalService;
import com.bienestar.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final EstudianteService estudianteService;
    private final ProfesionalService profesionalService;
    private final HorarioService horarioService;
    private final ReporteService reporteService;

    // ── Estudiantes ───────────────────────────────────────────
    @GetMapping("/estudiantes")
    public ResponseEntity<List<EstudianteDTO.Response>> listarEstudiantes() {
        return ResponseEntity.ok(estudianteService.listarTodos());
    }

    @PostMapping("/estudiantes")
    public ResponseEntity<EstudianteDTO.Response> crearEstudiante(
            @Valid @RequestBody EstudianteDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estudianteService.crear(request));
    }

    @DeleteMapping("/estudiantes/{id}")
    public ResponseEntity<Void> desactivarEstudiante(@PathVariable Long id) {
        estudianteService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    // ── Profesionales ─────────────────────────────────────────
    @GetMapping("/profesionales")
    public ResponseEntity<List<ProfesionalDTO.Response>> listarProfesionales() {
        return ResponseEntity.ok(profesionalService.listarTodos());
    }

    @PostMapping("/profesionales")
    public ResponseEntity<ProfesionalDTO.Response> crearProfesional(
            @Valid @RequestBody ProfesionalDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profesionalService.crear(request));
    }

    // ── Horarios ──────────────────────────────────────────────
    @PostMapping("/horarios")
    public ResponseEntity<HorarioDTO.Response> crearHorario(
            @Valid @RequestBody HorarioDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(horarioService.crear(request));
    }

    @GetMapping("/horarios/profesional/{profesionalId}")
    public ResponseEntity<List<HorarioDTO.Response>> horariosPorProfesional(
            @PathVariable Long profesionalId) {
        return ResponseEntity.ok(horarioService.listarPorProfesional(profesionalId));
    }

    @DeleteMapping("/horarios/{id}")
    public ResponseEntity<Void> desactivarHorario(@PathVariable Long id) {
        horarioService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    // ── Reportes ──────────────────────────────────────────────
    @GetMapping("/reportes/estadisticas")
    public ResponseEntity<ReporteDTO.EstadisticasBienestar> estadisticas() {
        return ResponseEntity.ok(reporteService.obtenerEstadisticas());
    }
}

package com.bienestar.controller;

import com.bienestar.dto.CitaDTO;
import com.bienestar.dto.HorarioDTO;
import com.bienestar.service.CitaService;
import com.bienestar.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesional")
@RequiredArgsConstructor
public class ProfesionalController {

    private final CitaService citaService;
    private final HorarioService horarioService;

    @GetMapping("/{usuarioId}/citas")
    public ResponseEntity<List<CitaDTO.Response>> getMisCitas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(citaService.listarPorProfesional(usuarioId));
    }

    @GetMapping("/{usuarioId}/horarios")
    public ResponseEntity<List<HorarioDTO.Response>> getMisHorarios(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(horarioService.listarPorUsuario(usuarioId));
    }

    // 🎯 NUEVO: Guardar horario propio
    @PostMapping("/{usuarioId}/horarios")
    public ResponseEntity<HorarioDTO.Response> crearMiHorario(
            @PathVariable Long usuarioId,
            @RequestBody HorarioDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(horarioService.crearDesdeProfesional(usuarioId, request));
    }
}
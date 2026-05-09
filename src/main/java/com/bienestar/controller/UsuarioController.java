package com.bienestar.controller;

import com.bienestar.dto.EstudianteDTO;
import com.bienestar.dto.ProfesionalDTO; // 🎯 Nuevo import
import com.bienestar.dto.UsuarioDTO;
import com.bienestar.service.EstudianteService;
import com.bienestar.service.ProfesionalService; // 🎯 Nuevo import
import com.bienestar.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EstudianteService estudianteService;
    private final ProfesionalService profesionalService; // 🎯 Inyectamos el servicio del profesional

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO.LoginResponse> login(
            @Valid @RequestBody UsuarioDTO.LoginRequest request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }

    // ─── 1. REGISTRO DE ESTUDIANTE ───
    @PostMapping("/registro/estudiante")
    public ResponseEntity<EstudianteDTO.Response> registroEstudiante(
            @Valid @RequestBody EstudianteDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estudianteService.crear(request));
    }

    // ─── 2. REGISTRO DE PROFESIONAL ───
    @PostMapping("/registro/profesional")
    public ResponseEntity<ProfesionalDTO.Response> registroProfesional(
            @Valid @RequestBody ProfesionalDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profesionalService.crear(request));
    }
}
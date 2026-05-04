package com.bienestar.controller;

import com.bienestar.dto.EstudianteDTO;
import com.bienestar.dto.UsuarioDTO;
import com.bienestar.service.EstudianteService;
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

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO.LoginResponse> login(
            @Valid @RequestBody UsuarioDTO.LoginRequest request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }

    @PostMapping("/registro")
    public ResponseEntity<EstudianteDTO.Response> registro(
            @Valid @RequestBody EstudianteDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estudianteService.crear(request));
    }
}

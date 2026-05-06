package com.bienestar.controller;

import com.bienestar.dto.SeguimientoDTO;
import com.bienestar.service.SeguimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seguimientos") // ⚠️ Verifica que esta ruta coincida con tu ApiService en Android
@RequiredArgsConstructor
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    // Recibe el POST desde la app del profesional
    @PostMapping
    public ResponseEntity<SeguimientoDTO.Response> registrarSeguimiento(
            @Valid @RequestBody SeguimientoDTO.Request request) {

        SeguimientoDTO.Response response = seguimientoService.crear(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Por si luego quieres ver el historial de una solicitud específica
    @GetMapping("/solicitud/{solicitudId}")
    public ResponseEntity<List<SeguimientoDTO.Response>> listarPorSolicitud(
            @PathVariable Long solicitudId) {
        return ResponseEntity.ok(seguimientoService.listarPorSolicitud(solicitudId));
    }
}
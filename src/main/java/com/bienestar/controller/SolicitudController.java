package com.bienestar.controller;

import com.bienestar.dto.SolicitudDTO;
import com.bienestar.service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudService solicitudService;

    // 🎯 Este es el que llama tu Android: viewModel.pedirApoyo(1L, body)
    @PostMapping("/estudiante/{estudianteId}")
    public ResponseEntity<SolicitudDTO.Response> crear(
            @PathVariable Long estudianteId,
            @Valid @RequestBody SolicitudDTO.Request request) {

        SolicitudDTO.Response response = solicitudService.crear(estudianteId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<SolicitudDTO.Response>> listarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(solicitudService.listarPorEstudiante(estudianteId));
    }

    @GetMapping
    public ResponseEntity<List<SolicitudDTO.Response>> listarTodas() {
        return ResponseEntity.ok(solicitudService.listarTodas());
    }
}
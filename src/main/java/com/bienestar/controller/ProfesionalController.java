package com.bienestar.controller;

import com.bienestar.dto.CitaDTO;
import com.bienestar.dto.HorarioDTO;
import com.bienestar.service.CitaService;
import com.bienestar.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesional")
@RequiredArgsConstructor
public class ProfesionalController {

    private final CitaService citaService;
    private final HorarioService horarioService;

    // 🎯 VER MIS CITAS: Usa el usuarioId de la sesión de Android
    @GetMapping("/{usuarioId}/citas")
    public ResponseEntity<List<CitaDTO.Response>> getMisCitas(@PathVariable Long usuarioId) {
        // El servicio debe usar findByProfesional_Usuario_Id
        return ResponseEntity.ok(citaService.listarPorProfesional(usuarioId));
    }

    // 🎯 VER MIS HORARIOS: Para que el Dr. Pérez no vea los de Laura
    @GetMapping("/{usuarioId}/horarios")
    public ResponseEntity<List<HorarioDTO.Response>> getMisHorarios(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(horarioService.listarPorUsuario(usuarioId)); // 🎯 Cambiado a listarPorUsuario
    }
}
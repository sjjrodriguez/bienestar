package com.bienestar.dto;

import com.bienestar.model.EstadoSolicitud;
import com.bienestar.model.TipoSolicitud;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class SolicitudDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private TipoSolicitud tipo;
        private String descripcion;
    }

    // 🎯 FIX 1: Esta clase es la que le faltaba al Service
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActualizarEstadoRequest {
        @NotNull
        private EstadoSolicitud estado;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long estudianteId;
        private String nombreEstudiante;
        private TipoSolicitud tipo;
        private String descripcion;
        private EstadoSolicitud estado;
        // 🎯 FIX 2: Agregamos este campo para que el Builder del Service funcione
        private String nombreProfesional;
        private LocalDateTime createdAt;
    }
}
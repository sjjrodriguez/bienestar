package com.bienestar.dto;

import com.bienestar.model.EstadoSolicitud;
import com.bienestar.model.TipoSolicitud;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class SolicitudDTO {

    @Data
    public static class Request {
        @NotNull private TipoSolicitud tipo;
    }

    @Data
    public static class ActualizarEstadoRequest {
        @NotNull private EstadoSolicitud estado;
    }

    @Data
    @Builder
    public static class Response {
        private Long id;
        private Long estudianteId;
        private String nombreEstudiante;
        private TipoSolicitud tipo;
        private EstadoSolicitud estado;
        private LocalDateTime createdAt;
    }
}

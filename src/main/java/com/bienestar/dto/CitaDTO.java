package com.bienestar.dto;

import com.bienestar.model.EstadoCita;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CitaDTO {

    @Data
    public static class Request {
        @NotNull private Long profesionalId;
        @NotNull private Long horarioId;
        @NotNull private LocalDate fecha;
        private String motivo;
    }

    @Data
    public static class ActualizarEstadoRequest {
        @NotNull private EstadoCita estado;
        private String notaProfesional;
    }

    @Data
    @Builder
    public static class Response {
        private Long id;
        private Long estudianteId;
        private String nombreEstudiante;
        private Long profesionalId;
        private String nombreProfesional;
        private Long horarioId;
        private String dia;
        private String horaInicio;
        private LocalDate fecha;
        private EstadoCita estado;
        private String motivo;
        private String notaProfesional;
        private LocalDateTime createdAt;
    }
}

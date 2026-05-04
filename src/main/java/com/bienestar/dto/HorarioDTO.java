package com.bienestar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

public class HorarioDTO {

    @Data
    public static class Request {
        @NotNull private Long profesionalId;
        @NotBlank private String dia;
        @NotNull private LocalTime horaInicio;
        @NotNull private LocalTime horaFin;
    }

    @Data
    @Builder
    public static class Response {
        private Long id;
        private Long profesionalId;
        private String nombreProfesional;
        private String dia;
        private LocalTime horaInicio;
        private LocalTime horaFin;
        private boolean activo;
    }
}

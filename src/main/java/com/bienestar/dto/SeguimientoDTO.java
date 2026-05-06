package com.bienestar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class SeguimientoDTO {

    @Data
    public static class Request {
        @NotNull private Long solicitudId;
        @NotNull private Long profesionalId; // <-- ¡Añade esto para que haga match con Android!
        @NotBlank private String nota;
    }

    @Data
    @Builder
    public static class Response {
        private Long id;
        private Long solicitudId;
        private Long profesionalId;
        private String nombreProfesional;
        private String nota;
        private LocalDateTime fechaRegistro;
    }
}
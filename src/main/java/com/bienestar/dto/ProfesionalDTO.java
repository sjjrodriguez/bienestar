package com.bienestar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class ProfesionalDTO {

    @Data
    public static class Request {
        @NotBlank private String nombre;
        @Email @NotBlank private String correo;
        @NotBlank private String contrasena;
        @NotBlank private String especialidad;
    }

    @Data
    @Builder
    public static class Response {
        private Long id;
        private Long usuarioId;
        private String nombre;
        private String correo;
        private String especialidad;
        private boolean activo;
    }
}

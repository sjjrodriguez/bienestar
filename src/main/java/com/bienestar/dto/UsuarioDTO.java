package com.bienestar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class UsuarioDTO {

    @Data
    public static class LoginRequest {
        @Email @NotBlank
        private String correo;
        @NotBlank
        private String contrasena;
    }

    @Data
    @Builder
    public static class LoginResponse {
        private Long id;
        private String nombre;
        private String correo;
        private String rol;
    }
}

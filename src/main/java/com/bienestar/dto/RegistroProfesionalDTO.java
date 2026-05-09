package com.bienestar.dto;

import lombok.Data;

@Data
public class RegistroProfesionalDTO {
    private String nombre;
    private String correo;
    private String contrasena;
    // Campo específico del profesional
    private String especialidad;
}
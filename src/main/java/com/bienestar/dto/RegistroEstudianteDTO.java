package com.bienestar.dto;

import lombok.Data;

@Data
public class RegistroEstudianteDTO {
    private String nombre;
    private String correo;
    private String contrasena;
    // Campos específicos del estudiante
    private String codigo;
    private String programa;
}
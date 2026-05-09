package com.bienestar.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes") // 🎯 CAMBIO 1: Apuntamos a la tabla donde están los datos
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_id", nullable = false)
    private Profesional profesional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id") // Quitamos nullable=false porque en tu DB está en NULL
    private Horario horario;

    private LocalDate fecha; // En tu DB está en NULL, ojo ahí

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoCita estado = EstadoCita.PENDIENTE;

    // 🎯 CAMBIO 2: Java usa 'motivo', pero en Supabase los datos están en 'descripcion'
    @Column(name = "descripcion")
    private String motivo;

    @Column(name = "nota_profesional")
    private String notaProfesional;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
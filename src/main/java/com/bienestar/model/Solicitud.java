package com.bienestar.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    // 🎯 NUEVO: Relación con el Profesional (necesaria para el Repository)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    // 🎯 NUEVO: Relación con el Horario (para saber la hora de la cita)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id")
    private Horario horario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSolicitud tipo;

    // Usamos 'motivo' para coincidir con tu base de datos
    @Column(columnDefinition = "TEXT")
    private String motivo;

    // 🎯 NUEVO: Campo de fecha para la cita
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
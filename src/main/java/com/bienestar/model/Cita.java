package com.bienestar.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas") // 🎯 CAMBIO 1: Apuntar a la tabla 'citas', NO a 'solicitudes'
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
    @JoinColumn(name = "horario_id")
    private Horario horario;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoCita estado = EstadoCita.PENDIENTE;

    // 🎯 CAMBIO 2: Verifica en tu tabla 'citas' si la columna se llama 'motivo' o 'descripcion'
    // Si en la DB es 'motivo', quita el @Column o cámbialo a name = "motivo"
    @Column(name = "motivo")
    private String motivo;

    @Column(name = "nota_profesional")
    private String notaProfesional;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
package com.bienestar.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

public class ReporteDTO {

    @Data
    @Builder
    public static class EstadisticasBienestar {
        private long totalEstudiantes;
        private long estudiantesConSolicitud;
        private Map<String, Long> solicitudesPorTipo;
        private Map<String, Long> citasPorEstado;
        private long totalCitasHoy;
    }
}

package com.bienestar.service.impl;

import com.bienestar.dto.ReporteDTO;
import com.bienestar.model.EstadoCita;
import com.bienestar.repository.CitaRepository;
import com.bienestar.repository.EstudianteRepository;
import com.bienestar.repository.SolicitudRepository;
import com.bienestar.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final EstudianteRepository estudianteRepository;
    private final SolicitudRepository solicitudRepository;
    private final CitaRepository citaRepository;

    @Override
    public ReporteDTO.EstadisticasBienestar obtenerEstadisticas() {
        long totalEstudiantes = estudianteRepository.count();

        Map<String, Long> solicitudesPorTipo = new HashMap<>();
        List<Object[]> filasTipo = solicitudRepository.countEstudiantesPorTipo();
        for (Object[] fila : filasTipo) {
            solicitudesPorTipo.put(fila[0].toString(), (Long) fila[1]);
        }

        long estudiantesConSolicitud = solicitudesPorTipo.values().stream()
                .mapToLong(Long::longValue).sum();

        Map<String, Long> citasPorEstado = new HashMap<>();
        for (EstadoCita e : EstadoCita.values()) {
            citasPorEstado.put(e.name(), 0L);
        }
        List<Object[]> filasCitas = citaRepository.countPorEstado();
        for (Object[] fila : filasCitas) {
            citasPorEstado.put(fila[0].toString(), (Long) fila[1]);
        }

        long totalCitasHoy = citaRepository.findAll().stream()
                .filter(c -> c.getFecha().equals(LocalDate.now()))
                .count();

        return ReporteDTO.EstadisticasBienestar.builder()
                .totalEstudiantes(totalEstudiantes)
                .estudiantesConSolicitud(estudiantesConSolicitud)
                .solicitudesPorTipo(solicitudesPorTipo)
                .citasPorEstado(citasPorEstado)
                .totalCitasHoy(totalCitasHoy)
                .build();
    }
}

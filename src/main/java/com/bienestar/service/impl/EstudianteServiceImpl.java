package com.bienestar.service.impl;

import com.bienestar.dto.EstudianteDTO;
import com.bienestar.model.Estudiante;
import com.bienestar.model.Rol;
import com.bienestar.model.Usuario;
import com.bienestar.repository.EstudianteRepository;
import com.bienestar.repository.UsuarioRepository;
import com.bienestar.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public EstudianteDTO.Response crear(EstudianteDTO.Request request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        if (estudianteRepository.existsByCodigo(request.getCodigo())) {
            throw new RuntimeException("El código estudiantil ya existe");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .contrasena(request.getContrasena())
                .rol(Rol.ESTUDIANTE)
                .build();
        usuario = usuarioRepository.save(usuario);

        Estudiante estudiante = Estudiante.builder()
                .usuario(usuario)
                .codigo(request.getCodigo())
                .programa(request.getPrograma())
                .build();

        return toResponse(estudianteRepository.save(estudiante));
    }

    @Override
    public List<EstudianteDTO.Response> listarTodos() {
        return estudianteRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EstudianteDTO.Response buscarPorId(Long id) {
        return toResponse(estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado")));
    }

    @Override
    @Transactional
    public void desactivar(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        estudiante.getUsuario().setActivo(false);
        usuarioRepository.save(estudiante.getUsuario());
    }

    private EstudianteDTO.Response toResponse(Estudiante e) {
        return EstudianteDTO.Response.builder()
                .id(e.getId())
                .usuarioId(e.getUsuario().getId())
                .nombre(e.getUsuario().getNombre())
                .correo(e.getUsuario().getCorreo())
                .codigo(e.getCodigo())
                .programa(e.getPrograma())
                .activo(e.getUsuario().isActivo())
                .build();
    }
}

package com.bienestar.service.impl;

import com.bienestar.dto.ProfesionalDTO;
import com.bienestar.model.Profesional;
import com.bienestar.model.Rol;
import com.bienestar.model.Usuario;
import com.bienestar.repository.ProfesionalRepository;
import com.bienestar.repository.UsuarioRepository;
import com.bienestar.service.ProfesionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfesionalServiceImpl implements ProfesionalService {

    private final ProfesionalRepository profesionalRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public ProfesionalDTO.Response crear(ProfesionalDTO.Request request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .contrasena(request.getContrasena())
                .rol(Rol.PROFESIONAL)
                .build();
        usuario = usuarioRepository.save(usuario);

        Profesional profesional = Profesional.builder()
                .usuario(usuario)
                .especialidad(request.getEspecialidad())
                .build();

        return toResponse(profesionalRepository.save(profesional));
    }

    @Override
    public List<ProfesionalDTO.Response> listarTodos() {
        return profesionalRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ProfesionalDTO.Response toResponse(Profesional p) {
        return ProfesionalDTO.Response.builder()
                .id(p.getId())
                .usuarioId(p.getUsuario().getId())
                .nombre(p.getUsuario().getNombre())
                .correo(p.getUsuario().getCorreo())
                .especialidad(p.getEspecialidad())
                .activo(p.getUsuario().isActivo())
                .build();
    }
}

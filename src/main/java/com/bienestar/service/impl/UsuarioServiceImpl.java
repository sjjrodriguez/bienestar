package com.bienestar.service.impl;

import com.bienestar.dto.UsuarioDTO;
import com.bienestar.model.Usuario;
import com.bienestar.repository.UsuarioRepository;
import com.bienestar.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO.LoginResponse login(UsuarioDTO.LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Correo o contraseña incorrectos"));

        if (!usuario.isActivo()) {
            throw new RuntimeException("Usuario desactivado");
        }

        if (!request.getContrasena().equals(usuario.getContrasena())) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }

        return UsuarioDTO.LoginResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol().name())
                .build();
    }
}

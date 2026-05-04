package com.bienestar.service;

import com.bienestar.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO.LoginResponse login(UsuarioDTO.LoginRequest request);
}

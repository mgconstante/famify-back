package br.edu.unifio.famify.services;

import br.edu.unifio.famify.dto.UsuarioDTO;
import br.edu.unifio.famify.repositorios.UsuarioRepositorio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<UsuarioDTO> listarUsuarios() {

        return usuarioRepositorio.findAll()
                .stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getSteamId()
                ))
                .toList();
    }
}
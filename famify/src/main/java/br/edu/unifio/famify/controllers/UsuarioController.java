
package br.edu.unifio.famify.controllers;

import br.edu.unifio.famify.dto.UsuarioDTO;
import br.edu.unifio.famify.services.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {

        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();

        return ResponseEntity.ok(usuarios);
    }
}

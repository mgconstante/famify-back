
package br.edu.unifio.famify.controllers;

import br.edu.unifio.famify.dto.CadastroUsuarioDTO;
import br.edu.unifio.famify.dto.UsuarioDTO;
import br.edu.unifio.famify.services.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody CadastroUsuarioDTO dados ) {
        UsuarioDTO usuario = usuarioService.cadastrarUsuario(dados);
        
        return ResponseEntity.created(URI.create("/usuarios/"+usuario.id())).body(usuario);
    }
    
}

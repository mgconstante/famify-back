
package br.edu.unifio.famify.controllers;

import br.edu.unifio.famify.dto.JogoDTO;
import br.edu.unifio.famify.services.BibliotecaService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bibliotecas")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    public BibliotecaController(
            BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping("/importar/{usuarioId}")
    public ResponseEntity<String> importarBiblioteca(
            @PathVariable Integer usuarioId) {

        bibliotecaService.importarBiblioteca(usuarioId);

        return ResponseEntity.ok(
                "Biblioteca importada com sucesso"
        );
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<JogoDTO>> buscarBiblioteca(
            @PathVariable Integer usuarioId) {

        List<JogoDTO> jogos =
                bibliotecaService.buscarJogosPorUsuario(usuarioId);

        return ResponseEntity.ok(jogos);
    }
}

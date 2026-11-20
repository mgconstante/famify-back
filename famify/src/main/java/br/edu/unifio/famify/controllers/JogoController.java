package br.edu.unifio.famify.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unifio.famify.dto.JogoDTO;
import br.edu.unifio.famify.services.JogoService;

@RestController
@RequestMapping("/jogos")
public class JogoController {
    private final JogoService jogoService;

    public JogoController(JogoService jogoService){
        this.jogoService = jogoService;
    }

    @GetMapping
    public ResponseEntity<List<JogoDTO>> listarUsuarios() {

        List<JogoDTO> jogos = jogoService.listarJogos();

        return ResponseEntity.ok(jogos);
    }
    
    @DeleteMapping("/{jogoId}")
    public ResponseEntity<Void> excluirJogo(
            @PathVariable Integer jogoId) {

        jogoService.excluirJogo(jogoId);

        return ResponseEntity.noContent().build();
    }
}


package br.edu.unifio.famify.controllers;

import br.edu.unifio.famify.dto.RecomendacaoDTO;
import br.edu.unifio.famify.services.RecomendacaoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;

    public RecomendacaoController(
            RecomendacaoService recomendacaoService) {

        this.recomendacaoService = recomendacaoService;
    }

    @GetMapping("/{usuarioId}/recomendacoes")
    public ResponseEntity<List<RecomendacaoDTO>> recomendar(
            @PathVariable Integer usuarioId) {

        List<RecomendacaoDTO> recomendacoes =
                recomendacaoService.recomendar(usuarioId);

        return ResponseEntity.ok(recomendacoes);
    }
}

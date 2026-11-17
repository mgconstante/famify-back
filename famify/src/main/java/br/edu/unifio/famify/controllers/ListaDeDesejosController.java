package br.edu.unifio.famify.controllers;
import br.edu.unifio.famify.services.ListaDeDesejosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listas-de-desejos")
public class ListaDeDesejosController {

    private final ListaDeDesejosService listaDeDesejosService;

    public ListaDeDesejosController(
            ListaDeDesejosService listaDeDesejosService) {

        this.listaDeDesejosService = listaDeDesejosService;
    }

    @PostMapping("/{usuarioId}/jogos/{jogoId}")
    public ResponseEntity<String> adicionarJogo(
            @PathVariable Integer usuarioId,
            @PathVariable Integer jogoId) {

        listaDeDesejosService.adicionarJogo(usuarioId, jogoId);

        return ResponseEntity.ok(
                "Jogo adicionado à lista de desejos com sucesso!"
        );
    }
}


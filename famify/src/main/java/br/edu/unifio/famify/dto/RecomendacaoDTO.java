
package br.edu.unifio.famify.dto;

import java.util.List;

public record RecomendacaoDTO(
        Integer usuarioId,
        String nome,
        double compatibilidade,
        int quantidadeJogos,
        List<String> jogosEncontrados
) {
}

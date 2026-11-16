package br.edu.unifio.famify.repositorios;

import org.junit.jupiter.api.Test;
import br.edu.unifio.famify.services.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JogoServiceTests {

    @Autowired
    private JogoService jogoService;

    @Test
    void testarImportacaoDeJogos() {

        jogoService.importarJogos("76561198417100326");

        System.out.println("Jogos importados com sucesso!");
    }
}
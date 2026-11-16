package br.edu.unifio.famify.repositorios;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.famify.dto.SteamGameDTO;
import br.edu.unifio.famify.services.SteamService;

@SpringBootTest 
public class SteamRepositorioTests {
@Autowired
private SteamService steamService;

@Test 
void testarBuscaDeJogos() {

    System.out.println(steamService);

    List<SteamGameDTO> jogos =
            steamService.buscarJogos("76561198417100326");

    for (SteamGameDTO jogo : jogos) {
        System.out.println(jogo.name());
        System.out.println(jogo.appid());
        System.out.println(jogo.playtime_forever());
    }
}
}

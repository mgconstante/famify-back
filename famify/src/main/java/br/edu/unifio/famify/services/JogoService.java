package br.edu.unifio.famify.services;

import br.edu.unifio.famify.dto.SteamGameDTO;
import br.edu.unifio.famify.dto.JogoDTO;
import br.edu.unifio.famify.entidades.Jogo;
import br.edu.unifio.famify.repositorios.JogoRepositorio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JogoService {

    private final SteamService steamService;
    private final JogoRepositorio jogoRepositorio;

    public JogoService(
            SteamService steamService,
            JogoRepositorio jogoRepositorio) {

        this.steamService = steamService;
        this.jogoRepositorio = jogoRepositorio;
    }

    @Transactional
    public void importarJogos(String steamId) {

        List<SteamGameDTO> jogos =
                steamService.buscarJogos(steamId);

        for (SteamGameDTO jogoSteam : jogos) {

            Jogo jogo = jogoRepositorio
                    .findByAppid(jogoSteam.appid())
                    .orElseGet(Jogo::new);

            jogo.setAppid(jogoSteam.appid());
            jogo.setNome(jogoSteam.name());

            jogoRepositorio.save(jogo);
        }
    }

    public List<JogoDTO> listarJogos() {

        return jogoRepositorio.findAll()
                .stream()
                .map(jogo -> new JogoDTO(
                    jogo.getId(),
                    jogo.getAppid(),
                    jogo.getNome()
                ))
                .toList();
    }
}
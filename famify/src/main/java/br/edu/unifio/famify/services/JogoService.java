package br.edu.unifio.famify.services;

import br.edu.unifio.famify.dto.SteamGameDTO;
import br.edu.unifio.famify.dto.JogoDTO;
import br.edu.unifio.famify.entidades.Jogo;
import br.edu.unifio.famify.repositorios.BibliotecaJogoRepositorio;
import br.edu.unifio.famify.repositorios.JogoRepositorio;
import br.edu.unifio.famify.repositorios.ListaDeDesejosJogoRepositorio;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JogoService {

    private final SteamService steamService;
    private final JogoRepositorio jogoRepositorio;    
    private final BibliotecaJogoRepositorio bibliotecaJogoRepositorio;
    private final ListaDeDesejosJogoRepositorio listaJogoRepositorio;

    public JogoService(
            SteamService steamService,
            JogoRepositorio jogoRepositorio,
            BibliotecaJogoRepositorio bibliotecaJogoRepositorio,
            ListaDeDesejosJogoRepositorio listaJogoRepositorio) {

        this.steamService = steamService;
        this.jogoRepositorio = jogoRepositorio;
        this.bibliotecaJogoRepositorio = bibliotecaJogoRepositorio;
        this.listaJogoRepositorio = listaJogoRepositorio;
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

    @Transactional
    public void excluirJogo(Integer jogoId) {

        Jogo jogo = jogoRepositorio.findById(jogoId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Jogo não encontrado"
                        ));

        if (bibliotecaJogoRepositorio.existsByJogo(jogo)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O jogo pertence à biblioteca de um usuário"
            );
        }

        if (listaJogoRepositorio.existsByJogo(jogo)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O jogo está na lista de desejos de um usuário"
            );
        }

        jogoRepositorio.delete(jogo);
    }
}
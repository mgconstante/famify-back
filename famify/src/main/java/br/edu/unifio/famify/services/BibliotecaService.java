package br.edu.unifio.famify.services;

import br.edu.unifio.famify.entidades.*;
import br.edu.unifio.famify.dto.SteamGameDTO;
import br.edu.unifio.famify.repositorios.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BibliotecaService {

    private final JogoRepositorio jogoRepositorio;
    private final BibliotecaJogoRepositorio bibliotecaJogosRepositorio;

    public BibliotecaService(
            JogoRepositorio jogoRepositorio,
            BibliotecaJogoRepositorio bibliotecaJogosRepositorio) {

        this.jogoRepositorio = jogoRepositorio;
        this.bibliotecaJogosRepositorio = bibliotecaJogosRepositorio;
    }

    @Transactional
    public void importarJogos(
            Biblioteca biblioteca,
            List<SteamGameDTO> jogosSteam) {

        for (SteamGameDTO jogoSteam : jogosSteam) {

            // Verifica se o jogo já existe
            Jogo jogo = jogoRepositorio
                    .findByAppid(jogoSteam.appid())
                    .orElseGet(() -> {

                        Jogo novoJogo = new Jogo();
                        novoJogo.setAppid(jogoSteam.appid());
                        novoJogo.setNome(jogoSteam.name());

                        return jogoRepositorio.save(novoJogo);
                    });

            
            boolean jaExiste =
                    bibliotecaJogosRepositorio
                            .existsByBibliotecaAndJogo(biblioteca, jogo);

            if (!jaExiste) {

                BibliotecaJogo bibliotecaJogo =
                        new BibliotecaJogo();

                bibliotecaJogo.setBiblioteca(biblioteca);
                bibliotecaJogo.setJogo(jogo);

                bibliotecaJogosRepositorio.save(bibliotecaJogo);
            }
        }
    }
}
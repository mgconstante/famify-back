
package br.edu.unifio.famify.services;

import br.edu.unifio.famify.dto.SteamGameDTO;
import br.edu.unifio.famify.entidades.*;
import br.edu.unifio.famify.repositorios.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BibliotecaService {

    private final SteamService steamService;
    private final UsuarioRepositorio usuarioRepositorio;
    private final BibliotecaRepositorio bibliotecaRepositorio;
    private final JogoRepositorio jogoRepositorio;
    private final BibliotecaJogoRepositorio bibliotecaJogoRepositorio;

    public BibliotecaService(
            SteamService steamService,
            UsuarioRepositorio usuarioRepositorio,
            BibliotecaRepositorio bibliotecaRepositorio,
            JogoRepositorio jogoRepositorio,
            BibliotecaJogoRepositorio bibliotecaJogoRepositorio) {

        this.steamService = steamService;
        this.usuarioRepositorio = usuarioRepositorio;
        this.bibliotecaRepositorio = bibliotecaRepositorio;
        this.jogoRepositorio = jogoRepositorio;
        this.bibliotecaJogoRepositorio = bibliotecaJogoRepositorio;
    }

    public void importarBiblioteca(Integer usuarioId) {

        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        if (usuario.getSteamId() == null ||
                usuario.getSteamId().isBlank()) {
            throw new IllegalArgumentException(
                    "Usuário não possui Steam ID");
        }

        // Busca os jogos na API da Steam
        List<SteamGameDTO> jogosSteam =
                steamService.buscarJogos(usuario.getSteamId());

        salvarJogos(usuario, jogosSteam);
    }

    @Transactional
    public void salvarJogos(
            Usuario usuario,
            List<SteamGameDTO> jogosSteam) {

        // Busca a biblioteca ou cria uma nova
        Biblioteca biblioteca = bibliotecaRepositorio
                .findByUsuario(usuario)
                .orElseGet(() -> {
                    Biblioteca nova = new Biblioteca();
                    nova.setUsuario(usuario);
                    return bibliotecaRepositorio.save(nova);
                });

        for (SteamGameDTO jogoSteam : jogosSteam) {

            // Busca o jogo no banco
            Jogo jogo = jogoRepositorio
                    .findByAppid(jogoSteam.appid())
                    .orElseGet(() -> {
                        Jogo novo = new Jogo();
                        novo.setAppid(jogoSteam.appid());
                        novo.setNome(jogoSteam.name());
                        return jogoRepositorio.save(novo);
                    });

            // Verifica se já pertence à biblioteca
            boolean existe = bibliotecaJogoRepositorio
                    .existsByBibliotecaAndJogo(biblioteca, jogo);

            if (!existe) {

                BibliotecaJogo bibliotecaJogo =
                        new BibliotecaJogo();

                bibliotecaJogo.setBiblioteca(biblioteca);
                bibliotecaJogo.setJogo(jogo);

                bibliotecaJogoRepositorio.save(bibliotecaJogo);
            }
        }
    }
}

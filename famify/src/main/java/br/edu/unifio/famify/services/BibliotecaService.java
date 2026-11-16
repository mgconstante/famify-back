
package br.edu.unifio.famify.services;

import br.edu.unifio.famify.dto.SteamGameDTO;
import br.edu.unifio.famify.entidades.*;
import br.edu.unifio.famify.repositorios.*;
import br.edu.unifio.famify.dto.JogoDTO;
import java.util.stream.Collectors;
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

        List<SteamGameDTO> jogosSteam =
                steamService.buscarJogos(usuario.getSteamId());

        salvarJogos(usuario, jogosSteam);
    }

    @Transactional
    public void salvarJogos(
            Usuario usuario,
            List<SteamGameDTO> jogosSteam) {

        
        Biblioteca biblioteca = bibliotecaRepositorio
                .findByUsuario(usuario)
                .orElseGet(() -> {
                    Biblioteca nova = new Biblioteca();
                    nova.setUsuario(usuario);
                    return bibliotecaRepositorio.save(nova);
                });

        for (SteamGameDTO jogoSteam : jogosSteam) {

            
            Jogo jogo = jogoRepositorio
                    .findByAppid(jogoSteam.appid())
                    .orElseGet(() -> {
                        Jogo novo = new Jogo();
                        novo.setAppid(jogoSteam.appid());
                        novo.setNome(jogoSteam.name());
                        return jogoRepositorio.save(novo);
                    });

            
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


@Transactional(readOnly = true)
public List<JogoDTO> buscarJogosPorUsuario(Integer usuarioId) {

    Usuario usuario = usuarioRepositorio.findById(usuarioId)
            .orElseThrow(() ->
                    new RuntimeException("Usuário não encontrado"));

    Biblioteca biblioteca = bibliotecaRepositorio
            .findByUsuario(usuario)
            .orElseThrow(() ->
                    new RuntimeException("Biblioteca não encontrada"));

    List<BibliotecaJogo> bibliotecaJogos =
            bibliotecaJogoRepositorio.findByBiblioteca(biblioteca);

    return bibliotecaJogos.stream()
            .map(bj -> new JogoDTO(
                    bj.getJogo().getId(),
                    bj.getJogo().getAppid(),
                    bj.getJogo().getNome()
            ))
            .collect(Collectors.toList());
}

}

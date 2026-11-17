
package br.edu.unifio.famify.services;

import br.edu.unifio.famify.dto.RecomendacaoDTO;
import br.edu.unifio.famify.entidades.*;
import br.edu.unifio.famify.repositorios.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RecomendacaoService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final ListaDeDesejosRepositorio listaRepositorio;
    private final ListaDeDesejosJogoRepositorio listaJogoRepositorio;
    private final BibliotecaRepositorio bibliotecaRepositorio;
    private final BibliotecaJogoRepositorio bibliotecaJogoRepositorio;

    public RecomendacaoService(
            UsuarioRepositorio usuarioRepositorio,
            ListaDeDesejosRepositorio listaRepositorio,
            ListaDeDesejosJogoRepositorio listaJogoRepositorio,
            BibliotecaRepositorio bibliotecaRepositorio,
            BibliotecaJogoRepositorio bibliotecaJogoRepositorio) {

        this.usuarioRepositorio = usuarioRepositorio;
        this.listaRepositorio = listaRepositorio;
        this.listaJogoRepositorio = listaJogoRepositorio;
        this.bibliotecaRepositorio = bibliotecaRepositorio;
        this.bibliotecaJogoRepositorio = bibliotecaJogoRepositorio;
    }

    @Transactional(readOnly = true)
    public List<RecomendacaoDTO> recomendar(Integer usuarioId) {

        
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuário não encontrado"));

        
        ListaDeDesejos lista = listaRepositorio.findByUsuario(usuario)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Lista de desejos não encontrada"));

        
        List<ListaDeDesejosJogo> desejos =
                listaJogoRepositorio.findByListaDeDesejos(lista);

        
        if (desejos.isEmpty()) {
            return List.of();
        }

        
        Map<Integer, String> jogosDesejados = new HashMap<>();

        for (ListaDeDesejosJogo desejo : desejos) {
            Jogo jogo = desejo.getJogo();
            jogosDesejados.put(jogo.getAppid(), jogo.getNome());
        }

        
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        List<RecomendacaoDTO> recomendacoes = new ArrayList<>();

        
        for (Usuario outroUsuario : usuarios) {

            
            if (outroUsuario.getId().equals(usuarioId)) {
                continue;
            }

            
            Optional<Biblioteca> bibliotecaOpt =
                    bibliotecaRepositorio.findByUsuario(outroUsuario);

            if (bibliotecaOpt.isEmpty()) {
                continue;
            }

            Biblioteca biblioteca = bibliotecaOpt.get();

            
            List<BibliotecaJogo> jogosBiblioteca =
                    bibliotecaJogoRepositorio.findByBiblioteca(biblioteca);

            List<String> jogosEncontrados = new ArrayList<>();
            Set<Integer> appidsEncontrados = new HashSet<>();

            
            for (BibliotecaJogo bibliotecaJogo : jogosBiblioteca) {

                Jogo jogo = bibliotecaJogo.getJogo();

                if (jogosDesejados.containsKey(jogo.getAppid())
                        && appidsEncontrados.add(jogo.getAppid())) {

                    jogosEncontrados.add(jogo.getNome());
                }
            }

            
            if (jogosEncontrados.isEmpty()) {
                continue;
            }

            
            double compatibilidade =
                    (double) jogosEncontrados.size()
                    / jogosDesejados.size() * 100;

            
            RecomendacaoDTO recomendacao = new RecomendacaoDTO(
                    outroUsuario.getId(),
                    outroUsuario.getNome(),
                    compatibilidade,
                    jogosEncontrados.size(),
                    jogosEncontrados
            );

            recomendacoes.add(recomendacao);
        }

        
        recomendacoes.sort(
                Comparator.comparingDouble(
                        RecomendacaoDTO::compatibilidade
                ).reversed()
        );

        return recomendacoes;
    }
}

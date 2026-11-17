
package br.edu.unifio.famify.services;

import br.edu.unifio.famify.entidades.Biblioteca;
import br.edu.unifio.famify.entidades.Jogo;
import br.edu.unifio.famify.entidades.ListaDeDesejos;
import br.edu.unifio.famify.entidades.ListaDeDesejosJogo;
import br.edu.unifio.famify.entidades.Usuario;
import br.edu.unifio.famify.exceptions.JogoJaExisteException;
import br.edu.unifio.famify.repositorios.BibliotecaJogoRepositorio;
import br.edu.unifio.famify.repositorios.BibliotecaRepositorio;
import br.edu.unifio.famify.repositorios.JogoRepositorio;
import br.edu.unifio.famify.repositorios.ListaDeDesejosJogoRepositorio;
import br.edu.unifio.famify.repositorios.ListaDeDesejosRepositorio;
import br.edu.unifio.famify.repositorios.UsuarioRepositorio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListaDeDesejosService {


private final ListaDeDesejosRepositorio listaDeDesejosRepositorio;
private final ListaDeDesejosJogoRepositorio listaDeDesejosJogoRepositorio;
private final UsuarioRepositorio usuarioRepositorio;
private final JogoRepositorio jogoRepositorio;
private final BibliotecaRepositorio bibliotecaRepositorio;
private final BibliotecaJogoRepositorio bibliotecaJogoRepositorio;

public ListaDeDesejosService(
        ListaDeDesejosRepositorio listaDeDesejosRepositorio,
        ListaDeDesejosJogoRepositorio listaDeDesejosJogoRepositorio,
        UsuarioRepositorio usuarioRepositorio,
        JogoRepositorio jogoRepositorio,
        BibliotecaRepositorio bibliotecaRepositorio,
        BibliotecaJogoRepositorio bibliotecaJogoRepositorio) {

    this.listaDeDesejosRepositorio = listaDeDesejosRepositorio;
    this.listaDeDesejosJogoRepositorio = listaDeDesejosJogoRepositorio;
    this.usuarioRepositorio = usuarioRepositorio;
    this.jogoRepositorio = jogoRepositorio;
    this.bibliotecaRepositorio = bibliotecaRepositorio;
    this.bibliotecaJogoRepositorio = bibliotecaJogoRepositorio;
}


    @Transactional
    public ListaDeDesejos criarListaDeDesejos(Usuario usuario) {

        return listaDeDesejosRepositorio.findByUsuario(usuario)
                .orElseGet(() -> {

                    ListaDeDesejos lista = new ListaDeDesejos();

                    lista.setUsuario(usuario);

                    return listaDeDesejosRepositorio.save(lista);
                });
    }



@Transactional
public void adicionarJogo(Integer usuarioId, Integer jogoId) {

    Usuario usuario = usuarioRepositorio.findById(usuarioId)
            .orElseThrow(() ->
                    new IllegalArgumentException("Usuário não encontrado"));

    ListaDeDesejos lista = criarListaDeDesejos(usuario);

    Jogo jogo = jogoRepositorio.findById(jogoId)
            .orElseThrow(() ->
                    new IllegalArgumentException("Jogo não encontrado"));

    boolean existeNaLista = listaDeDesejosJogoRepositorio
            .existsByListaDeDesejosAndJogo(lista, jogo);

    if (existeNaLista) {
        throw new JogoJaExisteException(
                "Esse jogo já está na lista de desejos!"
        );
    }

    
    Biblioteca biblioteca = bibliotecaRepositorio
            .findByUsuario(usuario)
            .orElse(null);

    
    if (biblioteca != null) {

        boolean possuiJogo = bibliotecaJogoRepositorio
                .existsByBibliotecaAndJogo(biblioteca, jogo);

        if (possuiJogo) {
            throw new JogoJaExisteException(
                    "Você já possui esse jogo na sua biblioteca!"
            );
        }
    }

    
    ListaDeDesejosJogo listaJogo = new ListaDeDesejosJogo();

    listaJogo.setListaDeDesejos(lista);
    listaJogo.setJogo(jogo);

    listaDeDesejosJogoRepositorio.save(listaJogo);
}


}

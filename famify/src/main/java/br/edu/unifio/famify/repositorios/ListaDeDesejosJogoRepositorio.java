package br.edu.unifio.famify.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unifio.famify.entidades.Jogo;
import br.edu.unifio.famify.entidades.ListaDeDesejos;
import br.edu.unifio.famify.entidades.ListaDeDesejosJogo;

public interface ListaDeDesejosJogoRepositorio
        extends JpaRepository<ListaDeDesejosJogo, Integer> {

    boolean existsByListaDeDesejosAndJogo(
            ListaDeDesejos listaDeDesejos,
            Jogo jogo
    );
}
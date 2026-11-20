package br.edu.unifio.famify.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unifio.famify.entidades.Jogo;
import br.edu.unifio.famify.entidades.ListaDeDesejos;
import br.edu.unifio.famify.entidades.ListaDeDesejosJogo;
import java.util.List;
import java.util.Optional;
public interface ListaDeDesejosJogoRepositorio
        extends JpaRepository<ListaDeDesejosJogo, Integer> {

    boolean existsByListaDeDesejosAndJogo(
            ListaDeDesejos listaDeDesejos,
            Jogo jogo
    );

    List<ListaDeDesejosJogo> findByListaDeDesejos(
        ListaDeDesejos listaDeDesejos
);
    Optional<ListaDeDesejosJogo> findByListaDeDesejosAndJogo(
        ListaDeDesejos listaDeDesejos,
        Jogo jogo
);
        boolean existsByJogo(Jogo jogo);
}
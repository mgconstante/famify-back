package br.edu.unifio.famify.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unifio.famify.entidades.Biblioteca;
import br.edu.unifio.famify.entidades.BibliotecaJogo;
import br.edu.unifio.famify.entidades.Jogo;


public interface BibliotecaJogoRepositorio extends JpaRepository<BibliotecaJogo,Integer>{

    boolean existsByBibliotecaAndJogo(
            Biblioteca biblioteca,
            Jogo jogo
    );
    
    List<BibliotecaJogo> findByBiblioteca(Biblioteca biblioteca);
}

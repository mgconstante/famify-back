package br.edu.unifio.famify.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unifio.famify.entidades.Jogo;

public interface JogoRepositorio extends JpaRepository<Jogo,Integer>{

    Optional<Jogo> findByAppid(Integer appid);
}

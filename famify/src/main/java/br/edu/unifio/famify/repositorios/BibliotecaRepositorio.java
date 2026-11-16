package br.edu.unifio.famify.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unifio.famify.entidades.Biblioteca;
import br.edu.unifio.famify.entidades.Usuario;

public interface BibliotecaRepositorio extends JpaRepository<Biblioteca,Integer>{
    Optional<Biblioteca> findByUsuario(Usuario usuario);
}

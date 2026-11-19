package br.edu.unifio.famify.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import br.edu.unifio.famify.entidades.ListaDeDesejos;

import br.edu.unifio.famify.entidades.Usuario;

public interface ListaDeDesejosRepositorio extends JpaRepository<ListaDeDesejos,Integer>{
    Optional<ListaDeDesejos> findByUsuario(Usuario usuario);


}

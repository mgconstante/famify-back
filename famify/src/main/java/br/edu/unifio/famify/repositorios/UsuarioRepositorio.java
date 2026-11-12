package br.edu.unifio.famify.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unifio.famify.entidades.Usuario;
public interface UsuarioRepositorio extends JpaRepository<Usuario,Integer>{

}

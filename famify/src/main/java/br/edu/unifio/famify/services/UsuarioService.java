package br.edu.unifio.famify.services;

import br.edu.unifio.famify.dto.CadastroUsuarioDTO;
import br.edu.unifio.famify.dto.UsuarioDTO;
import br.edu.unifio.famify.entidades.Usuario;
import br.edu.unifio.famify.exceptions.UsuarioJaExisteException;
import br.edu.unifio.famify.repositorios.UsuarioRepositorio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final BibliotecaService bibliotecaService;
    private final ListaDeDesejosService desejosService;

    public UsuarioService(
            UsuarioRepositorio usuarioRepositorio,
            BibliotecaService bibliotecaService,
            ListaDeDesejosService desejosService
        ) {

        this.usuarioRepositorio = usuarioRepositorio;
        this.bibliotecaService = bibliotecaService;
        this.desejosService = desejosService;
    }

    public List<UsuarioDTO> listarUsuarios() {

        return usuarioRepositorio.findAll()
                .stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getSteamId(),
                        usuario.getEmail()
                ))
                .toList();
    }

    public UsuarioDTO cadastrarUsuario(CadastroUsuarioDTO dados){
    if (usuarioRepositorio.existsBySteamId(dados.steamId())) {
        throw new UsuarioJaExisteException(
                "Já existe um usuário cadastrado com esse Steam ID!"
        );
    }

        Usuario usuario = new Usuario();
        usuario.setNome(dados.nome());
        usuario.setSenha(dados.senha());
        usuario.setSteamId(dados.steamId());
        usuario.setEmail(dados.email());

        Usuario usuarioSalvo = usuarioRepositorio.save(usuario);

        bibliotecaService.importarBiblioteca(usuarioSalvo.getId());

        desejosService.criarListaDeDesejos(usuarioSalvo);

    return new UsuarioDTO(
            usuarioSalvo.getId(),
            usuarioSalvo.getNome(),
            usuarioSalvo.getSteamId(),
            usuario.getEmail()
    );
    }
}
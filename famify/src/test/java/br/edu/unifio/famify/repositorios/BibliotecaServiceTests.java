package br.edu.unifio.famify.repositorios;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.famify.services.BibliotecaService;

@SpringBootTest
public class BibliotecaServiceTests {

    @Autowired
    private BibliotecaService bibliotecaService;

    @Test
    void testarImportacaoBiblioteca() {

        bibliotecaService.importarBiblioteca(2);

        System.out.println("Biblioteca importada!");
    }
}
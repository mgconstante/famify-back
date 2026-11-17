package br.edu.unifio.famify.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class JogoJaExisteException extends RuntimeException {

    public JogoJaExisteException(String mensagem) {
        super(mensagem);
    }
}
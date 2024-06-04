package br.com.johann.desafiohotel.domain.services.exceptions;

public class CheckInNaoEncontradoException extends RuntimeException {

    public CheckInNaoEncontradoException(String msg) {
        super(msg);
    }
}

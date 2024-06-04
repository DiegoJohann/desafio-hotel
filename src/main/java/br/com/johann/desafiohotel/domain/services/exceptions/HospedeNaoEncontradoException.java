package br.com.johann.desafiohotel.domain.services.exceptions;

public class HospedeNaoEncontradoException extends RuntimeException {

    public HospedeNaoEncontradoException(String msg) {
        super(msg);
    }
}

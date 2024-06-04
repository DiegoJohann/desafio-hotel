package br.com.johann.desafiohotel.domain.services.exceptions;

public class FiltroDesconhecidoException extends RuntimeException {

    public FiltroDesconhecidoException(String msg) {
        super(msg);
    }
}

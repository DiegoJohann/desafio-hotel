package br.com.johann.desafiohotel.domain.services.exceptions;

public class CheckoutInvalidoException extends RuntimeException {

    public CheckoutInvalidoException(String msg) {
        super(msg);
    }
}

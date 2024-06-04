package br.com.johann.desafiohotel.domain.services.exceptions;

public class CheckInPendenteException extends RuntimeException {

    public CheckInPendenteException(String msg) {
        super(msg);
    }
}

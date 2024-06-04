package br.com.johann.desafiohotel.domain.services.exceptions;

public class DataSaidaNullException extends RuntimeException {

    public DataSaidaNullException(String msg) {
        super(msg);
    }
}

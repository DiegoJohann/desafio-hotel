package br.com.johann.desafiohotel.exceptions;

import br.com.johann.desafiohotel.domain.services.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler({HospedeNaoEncontradoException.class, CheckInNaoEncontradoException.class})
    public ResponseEntity<ErroPadrao> hospedeNaoEncontrado(RuntimeException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ErroPadrao.builder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.NO_CONTENT.value())
                        .error(HttpStatus.NO_CONTENT.name())
                        .message(List.of(e.getMessage()))
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler({DataSaidaNullException.class, CheckoutInvalidoException.class, FiltroDesconhecidoException.class})
    public ResponseEntity<ErroPadrao> dataSaidaNull(RuntimeException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErroPadrao.builder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.name())
                        .message(List.of(e.getMessage()))
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(CheckInPendenteException.class)
    public ResponseEntity<ErroPadrao> checkInExistente(RuntimeException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErroPadrao.builder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.CONFLICT.value())
                        .error(HttpStatus.CONFLICT.name())
                        .message(List.of(e.getMessage()))
                        .path(request.getRequestURI())
                        .build());
    }
}

package com.silberlima.voto.api.exceptionhandler;

import com.silberlima.voto.domain.exception.EntidadeNaoEncontradaException;
import com.silberlima.voto.domain.exception.NegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<Problema.Campo> campos = ex.getBindingResult().getAllErrors().stream()
                .map(error -> Problema.Campo.builder()
                        .nome(((FieldError) error).getField())
                        .mensagem(messageSource.getMessage(error, LocaleContextHolder.getLocale()))
                        .build())
                .collect(Collectors.toList());

        Problema problema = Problema.builder()
                .status(status.value())
                .dataHora(OffsetDateTime.now())
                .titulo("Um ou mais campos estão inválidos")
                .detalhe("Preencha todos os campos obrigatórios")
                .campos(campos)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problema problema = criarProblemaBuilder(status, ex.getMessage()).build();
        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Problema problema = criarProblemaBuilder(status, ex.getMessage()).build();
        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    private Problema.ProblemaBuilder criarProblemaBuilder(HttpStatus status, String titulo) {
        return Problema.builder()
                .status(status.value())
                .dataHora(OffsetDateTime.now())
                .titulo(titulo);
    }
}

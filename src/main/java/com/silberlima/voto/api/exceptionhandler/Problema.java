package com.silberlima.voto.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problema {

    private Integer status;
    private OffsetDateTime dataHora;
    private String titulo;
    private String detalhe;
    private List<Campo> campos;

    @Getter
    @Builder
    public static class Campo {
        private String nome;
        private String mensagem;
    }
}

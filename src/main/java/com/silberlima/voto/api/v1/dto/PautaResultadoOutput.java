package com.silberlima.voto.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PautaResultadoOutput {

    private Long pautaId;
    private String titulo;
    private Long votosSim;
    private Long votosNao;
    private Long totalVotos;
    private String resultado;
    private boolean sessaoAberta;
}

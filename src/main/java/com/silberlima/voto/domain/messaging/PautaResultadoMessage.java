package com.silberlima.voto.domain.messaging;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PautaResultadoMessage {
    private Long pautaId;
    private String titulo;
    private Long votosSim;
    private Long votosNao;
    private String resultado;
}

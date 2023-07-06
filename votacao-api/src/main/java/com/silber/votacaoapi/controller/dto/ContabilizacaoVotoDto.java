package com.silber.votacaoapi.controller.dto;

import lombok.Data;

@Data
public class ContabilizacaoVotoDto {

    private PautaDto pauta;
    private Long votoSim;
    private Long votoNao;
}

package com.silber.votacaoapi.factory;

import com.silber.votacaoapi.controller.dto.PautaDto;
import com.silber.votacaoapi.domain.Pauta;

public class PautaDtoFactory {

    public static PautaDto buildFromEntity(Pauta pauta){
        return PautaDto.builder()
                .id(pauta.getId())
                .nome(pauta.getNome())
                .abertura(pauta.getAbertura())
                .encerramento(pauta.getEncerramento())
                .build();
    }
}

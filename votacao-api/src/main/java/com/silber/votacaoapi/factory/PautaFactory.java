package com.silber.votacaoapi.factory;

import com.silber.votacaoapi.controller.dto.PautaDto;
import com.silber.votacaoapi.domain.Pauta;

public class PautaFactory {

    public static Pauta buildFromDto(PautaDto pautaDto){
        return Pauta.builder()
                .id(pautaDto.getId())
                .nome(pautaDto.getNome())
                .abertura(pautaDto.getAbertura())
                .encerramento(pautaDto.getEncerramento())
                .build();

    }
}

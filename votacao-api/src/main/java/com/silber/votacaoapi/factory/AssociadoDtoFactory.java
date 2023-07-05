package com.silber.votacaoapi.factory;

import com.silber.votacaoapi.controller.dto.AssociadoDto;
import com.silber.votacaoapi.controller.dto.PautaDto;
import com.silber.votacaoapi.domain.Associado;

public class AssociadoDtoFactory {

    public static AssociadoDto buildFrom(Associado associado){

        return AssociadoDto.builder()
                .id(associado.getId())
                .cpf(associado.getCpf())
                .nome(associado.getNome())
                .build();

    }
}

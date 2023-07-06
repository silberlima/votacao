package com.silber.votacaoapi.factory;

import com.silber.votacaoapi.controller.dto.AssociadoDto;
import com.silber.votacaoapi.domain.Associado;

public class AssociadoFactory {

    private AssociadoFactory(){}
    public static Associado buildFromDto(AssociadoDto associadoDto){
        return Associado.builder()
                .id(associadoDto.getId())
                .cpf(associadoDto.getCpf())
                .nome(associadoDto.getNome())
                .build();
    }
}

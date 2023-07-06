package com.silber.votacaoapi.factory;

import com.silber.votacaoapi.controller.dto.VotacaoDto;
import com.silber.votacaoapi.domain.Votacao;

public class VotacaoDtoFactory {

    private VotacaoDtoFactory(){}
    public static VotacaoDto buildFromEntity (Votacao votacao){
        return VotacaoDto.builder()
                .id(votacao.getId())
                .pauta(PautaDtoFactory.buildFromEntity(votacao.getPauta()))
                .associado(AssociadoDtoFactory.buildFrom(votacao.getAssociado()))
                .voto(votacao.getVoto())
                .build();

    }
}

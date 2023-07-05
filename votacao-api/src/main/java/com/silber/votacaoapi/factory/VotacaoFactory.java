package com.silber.votacaoapi.factory;

import com.silber.votacaoapi.controller.dto.VotacaoDto;
import com.silber.votacaoapi.domain.Votacao;

public class VotacaoFactory {

    public static Votacao buildFrom(VotacaoDto votacaoDto){
        return Votacao.builder()
                .id(votacaoDto.getId())
                .pauta(PautaFactory.buildFromDto(votacaoDto.getPauta()))
                .associado(AssociadoFactory.buildFromDto(votacaoDto.getAssociado()))
                .voto(votacaoDto.getVoto())
                .build();
    }
}

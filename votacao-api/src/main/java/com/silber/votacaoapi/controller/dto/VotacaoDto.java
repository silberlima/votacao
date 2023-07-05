package com.silber.votacaoapi.controller.dto;

import com.silber.votacaoapi.domain.Associado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotacaoDto {

    private String id;

    private PautaDto pauta;

    private AssociadoDto associado;

    private String voto;

}

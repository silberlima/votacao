package com.silber.votacaoapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociadoDto {

    String id;

    @NotBlank
    String cpf;

    @NotBlank
    String nome;
}

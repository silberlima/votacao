package com.silberlima.voto.api.v1.dto;

import com.silberlima.voto.domain.model.VotoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotoInput {

    @NotBlank
    private String associadoId;

    @NotNull
    private VotoEnum valor;
}

package com.silber.votacaoapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PautaDto {

    private String id;

    @NotBlank
    @Size(min = 3, max = 10, message = "Tamanho entre 3 e 10")
    private String nome;
    private LocalDateTime abertura;
    private LocalDateTime encerramento;
}

package com.silberlima.voto.api.v1.controller;

import com.silberlima.voto.api.v1.dto.PautaInput;
import com.silberlima.voto.api.v1.dto.PautaOutput;
import com.silberlima.voto.domain.model.Pauta;
import com.silberlima.voto.domain.service.PautaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pautas")
@RequiredArgsConstructor
public class PautaController {

    private final PautaService pautaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PautaOutput cadastrar(@RequestBody @Valid PautaInput pautaInput) {
        Pauta pauta = Pauta.builder()
                .titulo(pautaInput.getTitulo())
                .descricao(pautaInput.getDescricao())
                .build();

        Pauta pautaSalva = pautaService.cadastrar(pauta);

        return PautaOutput.builder()
                .id(pautaSalva.getId())
                .titulo(pautaSalva.getTitulo())
                .descricao(pautaSalva.getDescricao())
                .build();
    }
}

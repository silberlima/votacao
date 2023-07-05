package com.silber.votacaoapi.controller;

import com.silber.votacaoapi.controller.dto.PautaDto;
import com.silber.votacaoapi.domain.Pauta;
import com.silber.votacaoapi.service.PautaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping(value = "/", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    private void salvarPauta(@Valid @RequestBody PautaDto pautaDto){
        var pauta = pautaService.salvar(Pauta.builder()
                .id(null)
                .nome(pautaDto.getNome())
                .dataCriacao(pautaDto.getDataCriacao())
                .build());

    }
}


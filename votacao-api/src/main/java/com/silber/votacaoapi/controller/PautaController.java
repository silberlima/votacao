package com.silber.votacaoapi.controller;

import com.silber.votacaoapi.controller.dto.PautaDto;
import com.silber.votacaoapi.factory.PautaDtoFactory;
import com.silber.votacaoapi.factory.PautaFactory;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PautaDto salvarPauta(@Valid @RequestBody PautaDto pautaDto){
        var pauta = pautaService.salvar(PautaFactory.buildFromDto(pautaDto));

        return PautaDtoFactory.buildFromEntity(pauta);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PautaDto abrirVotacao(@Valid @RequestBody PautaDto pautaDto){
        var pauta = pautaService.abrirVotacao(PautaFactory.buildFromDto(pautaDto));

        return PautaDtoFactory.buildFromEntity(pauta);
    }
}


package com.silber.votacaoapi.controller;

import com.silber.votacaoapi.controller.dto.VotacaoDto;
import com.silber.votacaoapi.factory.VotacaoDtoFactory;
import com.silber.votacaoapi.factory.VotacaoFactory;
import com.silber.votacaoapi.service.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votacao")
public class VotacaoController {

    @Autowired
    VotacaoService votacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VotacaoDto votar(@RequestBody VotacaoDto votacaoDto){
        var votacao = votacaoService.salvar(VotacaoFactory.buildFrom(votacaoDto));

        return VotacaoDtoFactory.buildFromEntity(votacao);
    }
}

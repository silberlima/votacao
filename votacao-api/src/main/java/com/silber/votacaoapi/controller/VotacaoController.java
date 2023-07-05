package com.silber.votacaoapi.controller;

import com.silber.votacaoapi.controller.dto.VotacaoDto;
import com.silber.votacaoapi.factory.VotacaoDtoFactory;
import com.silber.votacaoapi.factory.VotacaoFactory;
import com.silber.votacaoapi.service.VotacaoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/votacao")
public class VotacaoController {

    @Autowired
    VotacaoService votacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VotacaoDto> votar(@Valid @RequestBody VotacaoDto votacaoDto){
        try{
            var votacao = votacaoService.salvar(VotacaoFactory.buildFrom(votacaoDto));
            return ResponseEntity.ok(VotacaoDtoFactory.buildFromEntity(votacao));
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

package com.silber.votacaoapi.controller;

import com.silber.votacaoapi.controller.dto.AssociadoDto;
import com.silber.votacaoapi.factory.AssociadoDtoFactory;
import com.silber.votacaoapi.factory.AssociadoFactory;
import com.silber.votacaoapi.service.AssociadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/associado")
public class AssociadoController {

    @Autowired
    AssociadoService associadoService;

    @PostMapping
    public AssociadoDto salvar(@Valid @RequestBody AssociadoDto associadoDto){
        var associado = associadoService.salvar(AssociadoFactory.buildFromDto(associadoDto));
        return AssociadoDtoFactory.buildFrom(associado);
    }
}

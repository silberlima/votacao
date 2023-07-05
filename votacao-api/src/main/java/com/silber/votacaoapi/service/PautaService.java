package com.silber.votacaoapi.service;

import com.silber.votacaoapi.domain.Pauta;
import com.silber.votacaoapi.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta salvar(Pauta pauta){

        return pautaRepository.save(pauta);
    }

    public Pauta abrirVotacao(Pauta pauta){
/*
        if(pauta.getAbertura().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Não é permitido data retroativa");
        }

 */
        if(Objects.isNull(pauta.getAbertura())){
            pauta.setAbertura(LocalDateTime.now());
        }

        if (Objects.isNull(pauta.getEncerramento())){
            pauta.setEncerramento(LocalDateTime.now().plusMinutes(1));
        }

        return pautaRepository.save(pauta);
    }
}

package com.silber.votacaoapi.service;

import com.silber.votacaoapi.domain.Pauta;
import com.silber.votacaoapi.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        if(Objects.isNull(pauta.getAbertura())){
            pauta.setAbertura(LocalDateTime.now());
        }

        if (Objects.isNull(pauta.getEncerramento())){
            pauta.setEncerramento(LocalDateTime.now().plusMinutes(1));
        }else{
            if(LocalDateTime.now().isAfter(pauta.getEncerramento())){
                throw new RuntimeException("Data de encerramento inválida");
            }
        }

        return pautaRepository.save(pauta);
    }
}

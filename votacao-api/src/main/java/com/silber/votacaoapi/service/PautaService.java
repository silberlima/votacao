package com.silber.votacaoapi.service;

import com.silber.votacaoapi.domain.Pauta;
import com.silber.votacaoapi.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta salvar(Pauta pauta){

        if(pauta.getDataCriacao().isBefore(LocalDate.now())){
            throw new RuntimeException("Não é permitido data retroativa");
        }

        return pautaRepository.save(pauta);
    }
}

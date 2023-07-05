package com.silber.votacaoapi.service;

import com.silber.votacaoapi.domain.Associado;
import com.silber.votacaoapi.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService {

    @Autowired
    AssociadoRepository associadoRepository;

    public Associado salvar(Associado associado){
        return associadoRepository.save(associado);
    }

}

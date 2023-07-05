package com.silber.votacaoapi.service;

import com.silber.votacaoapi.domain.Votacao;
import com.silber.votacaoapi.repository.VotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotacaoService {
    @Autowired
    VotacaoRepository votacaoRepository;
    public Votacao salvar(Votacao votacao) {
        votacao = votacaoRepository.save(votacao);
        return votacao;
    }
}

package com.silber.votacaoapi.service;

import com.silber.votacaoapi.controller.dto.VotacaoDto;
import com.silber.votacaoapi.domain.Votacao;
import com.silber.votacaoapi.repository.VotacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
@Slf4j
@Service
public class VotacaoService {
    @Autowired
    VotacaoRepository votacaoRepository;
    @Transactional
    public Votacao salvar(Votacao votacao) throws Exception{


        if(LocalDateTime.now().isBefore(votacao.getPauta().getAbertura()) || LocalDateTime.now().isAfter(votacao.getPauta().getEncerramento())){

            throw new RuntimeException("Periodo de votação inválido");
        }

        var votacaoSalva = votacaoRepository.findByAssociadoAndPauta(votacao.getAssociado(), votacao.getPauta());

        if(Objects.nonNull(votacaoSalva)){
            throw new RuntimeException("Associado ja votou essa pauta");
        }

        return votacaoRepository.save(votacao);
    }
}

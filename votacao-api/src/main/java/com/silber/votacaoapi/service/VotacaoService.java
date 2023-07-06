package com.silber.votacaoapi.service;

import com.silber.votacaoapi.controller.dto.ContabilizacaoVotoDto;
import com.silber.votacaoapi.controller.dto.PautaDto;
import com.silber.votacaoapi.domain.Pauta;
import com.silber.votacaoapi.domain.Votacao;
import com.silber.votacaoapi.factory.PautaDtoFactory;
import com.silber.votacaoapi.factory.PautaFactory;
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

    public ContabilizacaoVotoDto contabilizarVotosPorPauta(PautaDto pautaDto){

        var pauta = PautaFactory.buildFromDto(pautaDto);
        var votacoes = votacaoRepository.findByPauta(pauta);
        ContabilizacaoVotoDto contabilizacaoVotoDto = new ContabilizacaoVotoDto();
        contabilizacaoVotoDto.setPauta(pautaDto);

        var totalSim = votacoes
                .stream()
                .filter(votacao -> votacao.getVoto().equals("SIM"))
                .count();

        var totalNao = votacoes
                .stream()
                .filter(votacao -> votacao.getVoto().equals("NAO"))
                .count();

        contabilizacaoVotoDto.setVotoSim(totalSim);
        contabilizacaoVotoDto.setVotoNao(totalNao);

        return contabilizacaoVotoDto;
    }
}

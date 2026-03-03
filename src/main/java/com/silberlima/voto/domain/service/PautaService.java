package com.silberlima.voto.domain.service;

import com.silberlima.voto.domain.model.Pauta;
import com.silberlima.voto.domain.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silberlima.voto.domain.model.Voto;
import com.silberlima.voto.domain.repository.VotoRepository;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;

    @Transactional
    public Pauta cadastrar(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    @Transactional
    public void abrirSessao(Long id, Long minutos) {
        Pauta pauta = buscar(id);

        if (pauta.getDataFechamento() != null) {
            throw new RuntimeException("Sessão já foi aberta para esta pauta");
        }

        long tempoSessao = (minutos == null || minutos <= 0) ? 1 : minutos;
        pauta.setDataFechamento(LocalDateTime.now().plusMinutes(tempoSessao));

        pautaRepository.save(pauta);
    }

    @Transactional
    public void votar(Long pautaId, Voto voto) {
        Pauta pauta = buscar(pautaId);

        if (!pauta.isSessaoAberta()) {
            throw new RuntimeException("Sessão de votação está fechada");
        }

        if (votoRepository.existsByPautaIdAndAssociadoId(pautaId, voto.getAssociadoId())) {
            throw new RuntimeException("Associado já votou nesta pauta");
        }

        voto.setPauta(pauta);
        votoRepository.save(voto);
    }

    public Pauta buscar(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));
    }
}

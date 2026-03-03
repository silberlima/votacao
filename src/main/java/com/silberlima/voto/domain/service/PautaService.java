package com.silberlima.voto.domain.service;

import com.silberlima.voto.domain.model.Pauta;
import com.silberlima.voto.domain.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silberlima.voto.domain.exception.EntidadeNaoEncontradaException;
import com.silberlima.voto.domain.exception.NegocioException;
import com.silberlima.voto.domain.model.VotoEnum;
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
            throw new NegocioException("Sessão já foi aberta para esta pauta");
        }

        long tempoSessao = (minutos == null || minutos <= 0) ? 1 : minutos;
        pauta.setDataFechamento(LocalDateTime.now().plusMinutes(tempoSessao));

        pautaRepository.save(pauta);
    }

    @Transactional
    public void votar(Long pautaId, Voto voto) {
        Pauta pauta = buscar(pautaId);

        if (!pauta.isSessaoAberta()) {
            throw new NegocioException("Sessão de votação está fechada");
        }

        if (votoRepository.existsByPautaIdAndAssociadoId(pautaId, voto.getAssociadoId())) {
            throw new NegocioException("Associado já votou nesta pauta");
        }

        voto.setPauta(pauta);
        votoRepository.save(voto);
    }

    public long contarVotos(Long pautaId, VotoEnum valor) {
        return votoRepository.countByPautaIdAndValor(pautaId, valor);
    }

    public long contarTotalVotos(Long pautaId) {
        return votoRepository.countByPautaId(pautaId);
    }

    public Pauta buscar(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pauta não encontrada com o ID: " + id));
    }
}

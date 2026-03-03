package com.silberlima.voto.domain.service;

import com.silberlima.voto.domain.model.Pauta;
import com.silberlima.voto.domain.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silberlima.voto.domain.client.CpfValidatorClient;
import com.silberlima.voto.domain.client.UserInfo;
import com.silberlima.voto.domain.exception.EntidadeNaoEncontradaException;
import com.silberlima.voto.domain.exception.NegocioException;
import com.silberlima.voto.domain.model.VotoEnum;
import com.silberlima.voto.domain.model.Voto;
import com.silberlima.voto.domain.repository.VotoRepository;
import feign.FeignException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;
    private final CpfValidatorClient cpfValidatorClient;

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

        validarAssociado(voto.getAssociadoId());

        voto.setPauta(pauta);
        votoRepository.save(voto);
    }

    private void validarAssociado(String cpf) {
        try {
            UserInfo userInfo = cpfValidatorClient.validateCpf(cpf);
            if (!userInfo.isAbleToVote()) {
                throw new NegocioException("Associado não está habilitado para votar (UNABLE_TO_VOTE)");
            }
        } catch (FeignException.NotFound e) {
            throw new NegocioException("CPF do associado inválido (Not found)");
        } catch (Exception e) {
            // Se o serviço externo estiver fora do ar, podemos escolher se barramos ou permitimos
            // Para este exercício, vamos barrar com uma mensagem clara
            throw new NegocioException("Erro ao validar CPF do associado: " + e.getMessage());
        }
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

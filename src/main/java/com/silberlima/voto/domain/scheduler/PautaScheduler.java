package com.silberlima.voto.domain.scheduler;

import com.silberlima.voto.domain.messaging.PautaProducer;
import com.silberlima.voto.domain.messaging.PautaResultadoMessage;
import com.silberlima.voto.domain.model.Pauta;
import com.silberlima.voto.domain.model.VotoEnum;
import com.silberlima.voto.domain.repository.PautaRepository;
import com.silberlima.voto.domain.service.PautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PautaScheduler {

    private final PautaRepository pautaRepository;
    private final PautaService pautaService;
    private final PautaProducer pautaProducer;

    @Scheduled(fixedDelay = 60000) // Executa a cada minuto
    @Transactional
    public void enviarResultadosPautasFechadas() {
        log.debug("Verificando pautas com sessões fechadas para envio de resultados");
        
        List<Pauta> pautasFechadas = pautaRepository.findByDataFechamentoBeforeAndResultadoEnviadoFalse(LocalDateTime.now());

        for (Pauta pauta : pautasFechadas) {
            long votosSim = pautaService.contarVotos(pauta.getId(), VotoEnum.SIM);
            long votosNao = pautaService.contarVotos(pauta.getId(), VotoEnum.NAO);

            String resultado = "Empate";
            if (votosSim > votosNao) {
                resultado = "Sim";
            } else if (votosNao > votosSim) {
                resultado = "Não";
            }

            PautaResultadoMessage message = PautaResultadoMessage.builder()
                    .pautaId(pauta.getId())
                    .titulo(pauta.getTitulo())
                    .votosSim(votosSim)
                    .votosNao(votosNao)
                    .resultado(resultado)
                    .build();

            pautaProducer.enviarResultado(message);
            pauta.setResultadoEnviado(true);
            pautaRepository.save(pauta);
        }
    }
}

package com.silberlima.voto.domain.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PautaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void enviarResultado(PautaResultadoMessage message) {
        log.info("Enviando resultado da pauta {} para o Kafka", message.getPautaId());
        kafkaTemplate.sendDefault(message.getPautaId().toString(), message);
    }
}

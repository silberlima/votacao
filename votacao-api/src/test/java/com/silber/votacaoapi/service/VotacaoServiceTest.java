package com.silber.votacaoapi.service;

import com.silber.votacaoapi.domain.Pauta;
import com.silber.votacaoapi.domain.Votacao;
import com.silber.votacaoapi.repository.VotacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class VotacaoServiceTest {
    @InjectMocks
    VotacaoService votacaoService;

    @Mock
    VotacaoRepository votacaoRepository;

    @Test
    void deve_retornar_erro_votacao_fora_periodo(){
        Pauta pauta = new Pauta(null, "nova pauta", LocalDateTime.now().minusDays(1), LocalDateTime.now());
        Votacao votacao = new Votacao(null, pauta,null,"SIM");

        var exception = Assertions.assertThrows(RuntimeException.class, () -> votacaoService.salvar(votacao));

        /*Verifica se houve interação com o repository*/
        Mockito.verifyNoInteractions(votacaoRepository);

        Assertions.assertEquals("Periodo de votação inválido", exception.getMessage());

    }

}

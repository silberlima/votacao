package com.silber.votacaoapi.service;

import com.silber.votacaoapi.domain.Associado;
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
class VotacaoServiceTest {

    /*O InjectMocks cria uma instância de VotacaoService e coloca os Mocks Do VotacaoRepository dentro*/
    @InjectMocks
    VotacaoService votacaoService;

    /*O Mock apenas cria o mock do repositorio*/
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

    @Test
    void deve_realizar_votacao() throws Exception {
        Pauta pauta = new Pauta("1", "nova pauta", LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        Associado associado = new Associado("1","1234567891233", "Joao");
        Votacao votacaoAntesSalvar = new Votacao(null, pauta, associado, "SIM");
        Votacao votacaoAposSalvar = votacaoAntesSalvar;
        votacaoAposSalvar.setId("1");

        /*Quando chamar o metodo save passando o objeto sem Id, ele vai retornar um objeto com Id*/
        Mockito.when(votacaoRepository.save(votacaoAntesSalvar)).thenReturn(votacaoAposSalvar);

        var votacao = votacaoService.salvar(votacaoAntesSalvar);

        /*Verificar se passou pelo repository.save*/
        Mockito.verify(votacaoRepository).save(votacaoAntesSalvar);

        /*Verifica se retornou o objeto de forma esperada*/
        Assertions.assertEquals(votacaoAposSalvar, votacao);
    }

}

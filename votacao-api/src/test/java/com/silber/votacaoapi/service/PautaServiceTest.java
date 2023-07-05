package com.silber.votacaoapi.service;

import com.silber.votacaoapi.domain.Pauta;
import com.silber.votacaoapi.repository.PautaRepository;
import com.silber.votacaoapi.service.PautaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class PautaServiceTest {

	@Mock
	private PautaRepository pautaRepository;

	@InjectMocks
	private PautaService pautaService;

	@Test
	void deve_salvar_pauta() {
		Pauta pautaAntesSalvar = new Pauta(null,"nova pauta", LocalDateTime.now(), null);
		Pauta pautaAposSalvar = pautaAntesSalvar;
		pautaAposSalvar.setId("1");

		Mockito.when(pautaRepository.save(pautaAntesSalvar)).thenReturn(pautaAposSalvar);

		var pauta = pautaService.salvar(pautaAntesSalvar);

		Mockito.verify(pautaRepository).save(pautaAntesSalvar);

		Assertions.assertEquals(pautaAposSalvar, pauta);
	}

	@Test
	void deve_retornar_erro_quando_data_menor_que_atual(){
		var dataEncerramentoAnteriorAtual = LocalDateTime.now().minusDays(1);
		Pauta pauta = new Pauta(null, "nova pauta", null, dataEncerramentoAnteriorAtual);

		var exception = Assertions.assertThrows(RuntimeException.class, () -> pautaService.abrirVotacao(pauta));

		/*Verifica se houve interação com o repository*/
		Mockito.verifyNoInteractions(pautaRepository);

		Assertions.assertEquals("Data de encerramento inválida", exception.getMessage());
	}



}

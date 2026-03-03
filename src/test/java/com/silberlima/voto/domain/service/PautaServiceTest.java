package com.silberlima.voto.domain.service;

import com.silberlima.voto.domain.client.CpfValidatorClient;
import com.silberlima.voto.domain.client.UserInfo;
import com.silberlima.voto.domain.exception.NegocioException;
import com.silberlima.voto.domain.model.Pauta;
import com.silberlima.voto.domain.model.Voto;
import com.silberlima.voto.domain.model.VotoEnum;
import com.silberlima.voto.domain.repository.PautaRepository;
import com.silberlima.voto.domain.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private CpfValidatorClient cpfValidatorClient;

    @InjectMocks
    private PautaService pautaService;

    private Pauta pauta;

    @BeforeEach
    void setUp() {
        pauta = Pauta.builder()
                .id(1L)
                .titulo("Pauta Teste")
                .descricao("Descrição Teste")
                .build();
    }

    @Test
    void deveAbrirSessaoComTempoDefaultQuandoMinutosForNulo() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        pautaService.abrirSessao(1L, null);

        assertNotNull(pauta.getDataFechamento());
        verify(pautaRepository).save(pauta);
    }

    @Test
    void deveLancarExcecaoAoAbrirSessaoJaAberta() {
        pauta.setDataFechamento(LocalDateTime.now().plusMinutes(1));
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        assertThrows(NegocioException.class, () -> pautaService.abrirSessao(1L, 5L));
    }

    @Test
    void deveLancarExcecaoQuandoAssociadoVotarDuasVezes() {
        pauta.setDataFechamento(LocalDateTime.now().plusMinutes(1));
        Voto voto = Voto.builder().associadoId("123").valor(VotoEnum.SIM).build();

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(votoRepository.existsByPautaIdAndAssociadoId(1L, "123")).thenReturn(true);

        assertThrows(NegocioException.class, () -> pautaService.votar(1L, voto));
    }

    @Test
    void deveLancarExcecaoQuandoCpfNaoHabilitadoParaVotar() {
        pauta.setDataFechamento(LocalDateTime.now().plusMinutes(1));
        Voto voto = Voto.builder().associadoId("123").valor(VotoEnum.SIM).build();
        UserInfo userInfo = new UserInfo();
        userInfo.setStatus("UNABLE_TO_VOTE");

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(votoRepository.existsByPautaIdAndAssociadoId(1L, "123")).thenReturn(false);
        when(cpfValidatorClient.validateCpf("123")).thenReturn(userInfo);

        assertThrows(NegocioException.class, () -> pautaService.votar(1L, voto));
    }
}

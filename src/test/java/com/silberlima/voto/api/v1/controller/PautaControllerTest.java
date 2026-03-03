package com.silberlima.voto.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silberlima.voto.api.v1.dto.VotoInput;
import com.silberlima.voto.domain.model.VotoEnum;
import com.silberlima.voto.api.v1.dto.SessaoInput;
import com.silberlima.voto.api.v1.dto.PautaInput;
import com.silberlima.voto.domain.model.Pauta;
import com.silberlima.voto.domain.model.Voto;
import com.silberlima.voto.domain.service.PautaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PautaController.class)
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PautaService pautaService;

    @Test
    void deveCadastrarPautaComSucesso() throws Exception {
        PautaInput input = PautaInput.builder()
                .titulo("Pauta Teste")
                .descricao("Descrição Teste")
                .build();

        Pauta pautaSalva = Pauta.builder()
                .id(1L)
                .titulo("Pauta Teste")
                .descricao("Descrição Teste")
                .build();

        when(pautaService.cadastrar(any(Pauta.class))).thenReturn(pautaSalva);

        mockMvc.perform(post("/v1/pautas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Pauta Teste"))
                .andExpect(jsonPath("$.descricao").value("Descrição Teste"));
    }

    @Test
    void deveRetornarBadRequestQuandoTituloEmBranco() throws Exception {
        PautaInput input = PautaInput.builder()
                .titulo("")
                .descricao("Descrição Teste")
                .build();

        mockMvc.perform(post("/v1/pautas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveAbrirSessaoComSucesso() throws Exception {
        SessaoInput input = SessaoInput.builder()
                .minutos(5L)
                .build();

        mockMvc.perform(post("/v1/pautas/1/abrir-sessao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNoContent());

        verify(pautaService).abrirSessao(eq(1L), eq(5L));
    }

    @Test
    void deveAbrirSessaoComTempoDefaultQuandoInputNulo() throws Exception {
        mockMvc.perform(post("/v1/pautas/1/abrir-sessao")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(pautaService).abrirSessao(eq(1L), eq(null));
    }

    @Test
    void deveVotarComSucesso() throws Exception {
        VotoInput input = VotoInput.builder()
                .associadoId("assoc-1")
                .valor(VotoEnum.SIM)
                .build();

        mockMvc.perform(post("/v1/pautas/1/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNoContent());

        verify(pautaService).votar(eq(1L), any(Voto.class));
    }

    @Test
    void deveRetornarBadRequestQuandoVotoInvalido() throws Exception {
        String inputJson = "{\"associadoId\": \"assoc-1\", \"valor\": \"TALVEZ\"}";

        mockMvc.perform(post("/v1/pautas/1/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveBuscarResultadoComSucesso() throws Exception {
        Pauta pauta = Pauta.builder()
                .id(1L)
                .titulo("Pauta Teste")
                .build();

        when(pautaService.buscar(1L)).thenReturn(pauta);
        when(pautaService.contarVotos(1L, VotoEnum.SIM)).thenReturn(10L);
        when(pautaService.contarVotos(1L, VotoEnum.NAO)).thenReturn(5L);
        when(pautaService.contarTotalVotos(1L)).thenReturn(15L);

        mockMvc.perform(get("/v1/pautas/1/resultado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pautaId").value(1L))
                .andExpect(jsonPath("$.titulo").value("Pauta Teste"))
                .andExpect(jsonPath("$.votosSim").value(10L))
                .andExpect(jsonPath("$.votosNao").value(5L))
                .andExpect(jsonPath("$.totalVotos").value(15L))
                .andExpect(jsonPath("$.resultado").value("Sim"));
    }
}

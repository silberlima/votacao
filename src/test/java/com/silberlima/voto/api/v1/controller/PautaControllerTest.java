package com.silberlima.voto.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silberlima.voto.api.v1.dto.PautaInput;
import com.silberlima.voto.domain.model.Pauta;
import com.silberlima.voto.domain.service.PautaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
}

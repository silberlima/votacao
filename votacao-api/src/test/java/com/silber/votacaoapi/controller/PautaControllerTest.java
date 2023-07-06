package com.silber.votacaoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silber.votacaoapi.controller.dto.PautaDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deve_retornar_nome_tamanho_erro() throws Exception {
        var pautaDto = new PautaDto( null, "Pauta", LocalDateTime.now(), null);
        mockMvc.perform(
                    post("/api/v1/pauta")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pautaDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath ("$.errors[?(@.fieldName == 'nome')].menssagem", Matchers.contains("Tamanho entre 3 e 10")));

    }
}

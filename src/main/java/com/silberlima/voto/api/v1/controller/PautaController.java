package com.silberlima.voto.api.v1.controller;

import com.silberlima.voto.api.v1.dto.PautaResultadoOutput;
import com.silberlima.voto.api.v1.dto.VotoInput;
import com.silberlima.voto.api.v1.dto.SessaoInput;
import com.silberlima.voto.api.v1.dto.PautaInput;
import com.silberlima.voto.api.v1.dto.PautaOutput;
import com.silberlima.voto.domain.model.Pauta;
import com.silberlima.voto.domain.model.Voto;
import com.silberlima.voto.domain.model.VotoEnum;
import com.silberlima.voto.domain.service.PautaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pautas")
@RequiredArgsConstructor
public class PautaController {

    private final PautaService pautaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PautaOutput cadastrar(@RequestBody @Valid PautaInput pautaInput) {
        Pauta pauta = Pauta.builder()
                .titulo(pautaInput.getTitulo())
                .descricao(pautaInput.getDescricao())
                .build();

        Pauta pautaSalva = pautaService.cadastrar(pauta);

        return mapToOutput(pautaSalva);
    }

    @PostMapping("/{id}/abrir-sessao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrirSessao(@PathVariable Long id, @RequestBody(required = false) SessaoInput sessaoInput) {
        Long minutos = sessaoInput != null ? sessaoInput.getMinutos() : null;
        pautaService.abrirSessao(id, minutos);
    }

    @PostMapping("/{id}/votos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void votar(@PathVariable Long id, @RequestBody @Valid VotoInput votoInput) {
        Voto voto = Voto.builder()
                .associadoId(votoInput.getAssociadoId())
                .valor(votoInput.getValor())
                .build();

        pautaService.votar(id, voto);
    }

    @GetMapping("/{id}/resultado")
    public PautaResultadoOutput buscarResultado(@PathVariable Long id) {
        Pauta pauta = pautaService.buscar(id);

        long votosSim = pautaService.contarVotos(id, VotoEnum.SIM);
        long votosNao = pautaService.contarVotos(id, VotoEnum.NAO);
        long totalVotos = pautaService.contarTotalVotos(id);

        String resultado = "Empate";
        if (votosSim > votosNao) {
            resultado = "Sim";
        } else if (votosNao > votosSim) {
            resultado = "Não";
        }

        return PautaResultadoOutput.builder()
                .pautaId(pauta.getId())
                .titulo(pauta.getTitulo())
                .votosSim(votosSim)
                .votosNao(votosNao)
                .totalVotos(totalVotos)
                .resultado(resultado)
                .sessaoAberta(pauta.isSessaoAberta())
                .build();
    }

    private PautaOutput mapToOutput(Pauta pauta) {
        return PautaOutput.builder()
                .id(pauta.getId())
                .titulo(pauta.getTitulo())
                .descricao(pauta.getDescricao())
                .build();
    }
}

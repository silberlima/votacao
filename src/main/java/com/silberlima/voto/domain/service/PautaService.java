package com.silberlima.voto.domain.service;

import com.silberlima.voto.domain.model.Pauta;
import com.silberlima.voto.domain.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;

    @Transactional
    public Pauta cadastrar(Pauta pauta) {
        return pautaRepository.save(pauta);
    }
}

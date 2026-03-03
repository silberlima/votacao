package com.silberlima.voto.domain.repository;

import com.silberlima.voto.domain.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    List<Pauta> findByDataFechamentoBeforeAndResultadoEnviadoFalse(LocalDateTime data);
}

package com.silberlima.voto.domain.repository;

import com.silberlima.voto.domain.model.Voto;
import com.silberlima.voto.domain.model.VotoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByPautaIdAndAssociadoId(Long pautaId, String associadoId);
    long countByPautaIdAndValor(Long pautaId, VotoEnum valor);
    long countByPautaId(Long pautaId);
}

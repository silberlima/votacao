package com.silber.votacaoapi.repository;

import com.silber.votacaoapi.domain.Associado;
import com.silber.votacaoapi.domain.Pauta;
import com.silber.votacaoapi.domain.Votacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotacaoRepository extends MongoRepository<Votacao, String> {

    Votacao findByAssociadoAndPauta(Associado associado, Pauta pauta);

    List<Votacao> findByPauta(Pauta pauta);
}

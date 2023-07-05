package com.silber.votacaoapi.repository;

import com.silber.votacaoapi.domain.Pauta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PautaRepository extends MongoRepository<Pauta, String> {
}

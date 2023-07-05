package com.silber.votacaoapi.repository;

import com.silber.votacaoapi.domain.Associado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends MongoRepository<Associado, String> {
}

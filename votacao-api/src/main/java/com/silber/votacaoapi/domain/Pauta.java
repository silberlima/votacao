package com.silber.votacaoapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "pauta")
public class Pauta {
    @MongoId
    private String id;
    private String nome;
    private LocalDateTime abertura;
    private LocalDateTime encerramento;
}

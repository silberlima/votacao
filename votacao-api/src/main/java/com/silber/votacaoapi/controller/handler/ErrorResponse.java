package com.silber.votacaoapi.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private Long timestamp;

    private HttpStatus httpStatus;

    private String message;

    private String path;

    private List<FieldMessage> errors;
}

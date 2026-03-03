package com.silberlima.voto.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpfValidator", url = "https://run.mocky.io/v3/57f23672-c15f-48f8-90d3-d84ce00250b8/users")
public interface CpfValidatorClient {

    @GetMapping("/{cpf}")
    UserInfo validateCpf(@PathVariable("cpf") String cpf);
}

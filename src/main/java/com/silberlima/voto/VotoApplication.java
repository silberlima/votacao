package com.silberlima.voto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class VotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotoApplication.class, args);
	}

}

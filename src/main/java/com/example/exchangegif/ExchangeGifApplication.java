package com.example.exchangegif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExchangeGifApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeGifApplication.class, args);
	}

}

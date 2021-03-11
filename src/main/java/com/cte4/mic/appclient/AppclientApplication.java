package com.cte4.mic.appclient;

import com.cte4.mic.appclient.annotation.cargo.MacCargo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppclientApplication.class, args);
	}

	@Bean
	public MacCargo initMAC() {
		return MacCargo.build();
	}

}

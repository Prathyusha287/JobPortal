package com.prathyusha.jobappms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class JobappmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobappmsApplication.class, args);
	}

}

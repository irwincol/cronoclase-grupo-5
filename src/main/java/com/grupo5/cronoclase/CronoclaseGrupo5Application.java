package com.grupo5.cronoclase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CronoclaseGrupo5Application {

	public static void main(String[] args) {
		SpringApplication.run(CronoclaseGrupo5Application.class, args);
	}

}

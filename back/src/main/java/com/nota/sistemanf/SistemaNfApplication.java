package com.nota.sistemanf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.nota.sistemanf.entidades.SequenciaNota;
import com.nota.sistemanf.repository.SequenciaNotaRepository;

@SpringBootApplication
public class SistemaNfApplication {
	public static void main(String[] args) {
		SpringApplication.run(SistemaNfApplication.class, args);
	}

	@Bean
	public String iniciaSequenciaNumeroNotas(SequenciaNotaRepository sqRepo) {
		SequenciaNota sq = sqRepo.findByFuncao("INCREMENTO");

		if (sq == null) {
			sqRepo.save(new SequenciaNota(null, "INCREMENTO", 0));
		}
		return null;
	}
}

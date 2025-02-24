package com.nota.sistemanf.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nota.sistemanf.entidades.SequenciaNota;

public interface SequenciaNotaRepository extends PagingAndSortingRepository<SequenciaNota, Integer> {

	@Query("SELECT sq.ultimoNumeroNota FROM SequenciaNota sq WHERE sq.funcao = 'INCREMENTO'")
	Integer ultimoNumero();

	SequenciaNota findByFuncao(String string);
}

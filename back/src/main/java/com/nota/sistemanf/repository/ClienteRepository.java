package com.nota.sistemanf.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nota.sistemanf.entidades.Cliente;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Integer> {

	Cliente findByNomeIgnoreCase(String nome);

	boolean existsByNomeIgnoreCase(String nome);

	@Query("SELECT count(*) FROM Cliente c JOIN Nota n ON c.id = :id AND c.id = n.cliente.id")
	Integer qtdNotasVinculadas(Integer id);

}

package com.nota.sistemanf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nota.sistemanf.entidades.Produto;

public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Integer> {

	Produto findByNomeIgnoreCase(String nome);

	List<Produto> findAll();

	@Query("SELECT count(*) FROM Produto p JOIN Item i ON p.id = :id AND p.id = i.produto.id ")
	Integer itensVinculados(Integer id);

}

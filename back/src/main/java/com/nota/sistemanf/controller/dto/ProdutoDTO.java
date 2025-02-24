package com.nota.sistemanf.controller.dto;

import java.math.BigDecimal;

import com.nota.sistemanf.entidades.Produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoDTO {

	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.preco = produto.getPreco();
	}

	private Integer id;
	private String nome;
	private BigDecimal preco;
}

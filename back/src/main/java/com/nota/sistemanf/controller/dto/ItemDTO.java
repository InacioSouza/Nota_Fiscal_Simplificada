package com.nota.sistemanf.controller.dto;

import java.math.BigDecimal;

import com.nota.sistemanf.entidades.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {

	public ItemDTO(Item itemSalvo) {
		this.id = itemSalvo.getId();
		this.produto = new ProdutoDTO(itemSalvo.getProduto());
		this.quantidade = itemSalvo.getQuantidade();
		this.valorTotal = itemSalvo.getValorTotal();
	}

	private Integer id;
	private ProdutoDTO produto;
	private Integer quantidade;
	private BigDecimal valorTotal;
}

package com.nota.sistemanf.entidades;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nota.sistemanf.controller.form.ProdutoFORM;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "produtos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private BigDecimal preco;

	public Produto(ProdutoFORM produtoForm) {
		this.nome = produtoForm.getNome();
		this.preco = produtoForm.getPreco();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((preco == null) ? 0 : preco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		Produto p = (Produto) obj;

		if (this.getNome() == p.getNome() && this.getPreco() == p.getPreco()) {
			return true;
		}
		return false;
	}

}

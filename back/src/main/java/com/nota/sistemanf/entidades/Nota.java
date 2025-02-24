package com.nota.sistemanf.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "notas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Nota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private int numero;

	private Date dataEmissao;

	@ManyToOne
	private Cliente cliente;

	@OneToMany(mappedBy = "nota", fetch = FetchType.EAGER)
	private List<Item> itens = new ArrayList<Item>();

	private BigDecimal valorTotal = new BigDecimal(0);

	public Nota(Cliente cliente, List<Item> itens, int numero) {
		this.cliente = cliente;
		this.itens = itens;

		this.numero = numero;
		calcValorTotalNota();
	}

	public void calcValorTotalNota() {
		BigDecimal valorTot = BigDecimal.ZERO;

		if (!itens.isEmpty()) {

			for (Item item : itens) {
				valorTot = valorTot.add(item.getValorTotal());
			}
		}
		this.valorTotal = valorTot;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
		calcValorTotalNota();
	}

	@PrePersist
	private void defineDataEmissao() {
		this.dataEmissao = new Date();
	}

}

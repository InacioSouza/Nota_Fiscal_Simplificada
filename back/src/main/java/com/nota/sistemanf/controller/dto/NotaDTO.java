package com.nota.sistemanf.controller.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.nota.sistemanf.entidades.Nota;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotaDTO {
	private Integer id;
	private Integer numero;
	private Date dataEmissao;
	private ClienteDTO cliente;
	private BigDecimal valorTotal;
	private List<ItemDTO> itens;

	public NotaDTO(Nota nota) {
		this.id = nota.getId();
		this.numero = nota.getNumero();
		this.dataEmissao = nota.getDataEmissao();
		this.cliente = new ClienteDTO(nota.getCliente());
		this.valorTotal = nota.getValorTotal();
		this.itens = nota.getItens().stream().map(ItemDTO::new).collect(Collectors.toList());
	}
}

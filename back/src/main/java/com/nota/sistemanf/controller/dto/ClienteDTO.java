package com.nota.sistemanf.controller.dto;

import com.nota.sistemanf.entidades.Cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteDTO {

	private Integer id;
	private String nome;

	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
	}

}

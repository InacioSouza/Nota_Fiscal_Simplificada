package com.nota.sistemanf.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoFORM {

	@NotBlank
	private String nome;
	@NotNull
	private BigDecimal preco;
}

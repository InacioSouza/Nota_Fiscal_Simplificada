package com.nota.sistemanf.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemFORM {

	@NotNull
	private Integer idProduto;

	@NotNull
	private Integer quantidade;

	private BigDecimal valorTotal;
}

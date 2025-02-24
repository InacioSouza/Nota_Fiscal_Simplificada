package com.nota.sistemanf.controller.form;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotaFORM {

	@NotNull
	private Integer idCliente;

	@NotNull
	@NotEmpty
	private List<ItemFORM> itens;

	private BigDecimal valorTotal;
}

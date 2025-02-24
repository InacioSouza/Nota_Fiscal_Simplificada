package com.nota.sistemanf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nota.sistemanf.controller.form.ProdutoFORM;
import com.nota.sistemanf.services.ProdutoService;

@RestController
@RequestMapping("snf/produto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@PostMapping
	public ResponseEntity<?> cadastra(@Valid @RequestBody ProdutoFORM produtoForm) {
		return service.cadastra(produtoForm);
	}

	@GetMapping
	public ResponseEntity<?> listaProdutos() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarProdutoPorId(@PathVariable Integer id) {
		return service.buscaProdutoPorID(id);
	}

	@GetMapping("/buscar")
	public ResponseEntity<?> buscarProdutoPorNome(@RequestParam String nome) {
		return service.buscarProdutoPorNome(nome);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletaProduto(@PathVariable Integer id) {
		return service.deletar(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizaProduto(@PathVariable Integer id,
			@RequestBody @Valid ProdutoFORM produtoAtualizado) {
		return service.atualizar(id, produtoAtualizado);
	}
}

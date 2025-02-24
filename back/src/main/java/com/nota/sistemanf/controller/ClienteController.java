package com.nota.sistemanf.controller;

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

import com.nota.sistemanf.controller.form.ClienteFORM;
import com.nota.sistemanf.services.ClienteService;

@RestController
@RequestMapping("/snf/cliente")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody ClienteFORM clienteForm) {
		return service.cadastrar(clienteForm);
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscaClientePorID(@PathVariable Integer id) {
		return service.buscaClientePorID(id);
	}

	@GetMapping("/buscar")
	public ResponseEntity<?> buscarClientesPorNome(@RequestParam String nome) {
		return service.buscaClienteporNome(nome);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ClienteFORM clienteAtualizado) {
		return service.atualizar(id, clienteAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletaClientePorId(@PathVariable Integer id) {
		return service.deletar(id);
	}
}

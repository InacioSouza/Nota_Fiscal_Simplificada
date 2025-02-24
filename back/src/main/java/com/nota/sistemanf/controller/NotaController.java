package com.nota.sistemanf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nota.sistemanf.controller.form.NotaFORM;
import com.nota.sistemanf.services.NotaService;

@RestController
@RequestMapping("/snf/nota")
@CrossOrigin(origins = "http://localhost:4200")
public class NotaController {

	@Autowired
	private NotaService service;

	@PostMapping
	public ResponseEntity<?> cadastraNota(@RequestBody @Valid NotaFORM notaForm) {
		return service.cadastrar(notaForm);
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		return service.listar();
	}

}

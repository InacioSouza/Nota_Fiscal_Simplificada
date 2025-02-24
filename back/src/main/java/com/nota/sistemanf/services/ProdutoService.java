package com.nota.sistemanf.services;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.nota.sistemanf.controller.dto.ProdutoDTO;
import com.nota.sistemanf.controller.form.ProdutoFORM;
import com.nota.sistemanf.entidades.Produto;
import com.nota.sistemanf.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepo;

	@Transactional
	public ResponseEntity<?> cadastra(ProdutoFORM produtoForm) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

		Produto produtoEncontrado = produtoRepo.findByNomeIgnoreCase(produtoForm.getNome());

		if (produtoEncontrado != null) {
			produtoEncontrado.setNome(produtoForm.getNome());
			produtoEncontrado.setPreco(produtoForm.getPreco());

			URI uri = uriBuilder.path("snf/produto/{id}").buildAndExpand(produtoEncontrado.getId()).toUri();
			return ResponseEntity.created(uri).body(new ProdutoDTO(produtoEncontrado));
		}

		Produto produtoSalvo = produtoRepo.save(new Produto(produtoForm));
		ProdutoDTO produtoSalvoDTO = new ProdutoDTO(produtoSalvo);

		URI url = uriBuilder.path("snf/produto/{id}").buildAndExpand(produtoSalvo.getId()).toUri();

		return ResponseEntity.ok(produtoSalvoDTO);
	}

	public ResponseEntity<?> listar() {
		List<ProdutoDTO> listProdutoDTO = produtoRepo.findAll().stream().map(ProdutoDTO::new)
				.collect(Collectors.toList());

		if (listProdutoDTO == null || listProdutoDTO.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto encontrado no banco");
		}
		return ResponseEntity.ok(listProdutoDTO);
	}

	@Transactional
	public ResponseEntity<?> buscaProdutoPorID(Integer id) {

		Produto produto = produtoRepo.findById(id).get();

		if (produto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto encontrado para o id: " + id);
		}

		return ResponseEntity.ok(new ProdutoDTO(produto));
	}

	@Transactional
	public ResponseEntity<?> buscarProdutoPorNome(String nome) {
		Produto produto = produtoRepo.findByNomeIgnoreCase(nome);
		if (produto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto encontrado para o nome: " + nome);
		}
		return ResponseEntity.ok(new ProdutoDTO(produto));
	}

	@Transactional
	public ResponseEntity<?> deletar(Integer id) {
		if (!produtoRepo.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto encontrado para o id: " + id);
		}

		if (this.qtdItensVinculados(id) != 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("O produto não pode ser deletado, existem itens vinculados a ele");
		}

		produtoRepo.deleteById(id);
		return ResponseEntity.ok("Produto deletado!");
	}

	public Integer qtdItensVinculados(Integer id) {
		return produtoRepo.itensVinculados(id);
	}

	@Transactional
	public ResponseEntity<?> atualizar(Integer id, ProdutoFORM produtoForm) {

		if (!produtoRepo.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto encontrado para o id: " + id);
		}

		Produto produtoAtualizado = produtoRepo.findById(id).get();
		produtoAtualizado.setNome(produtoForm.getNome());
		produtoAtualizado.setPreco(produtoForm.getPreco());

		return ResponseEntity.ok(produtoAtualizado);
	}

}

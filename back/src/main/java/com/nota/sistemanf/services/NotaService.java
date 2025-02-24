package com.nota.sistemanf.services;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.nota.sistemanf.controller.dto.NotaDTO;
import com.nota.sistemanf.controller.form.ItemFORM;
import com.nota.sistemanf.controller.form.NotaFORM;
import com.nota.sistemanf.entidades.Cliente;
import com.nota.sistemanf.entidades.Item;
import com.nota.sistemanf.entidades.Nota;
import com.nota.sistemanf.entidades.Produto;
import com.nota.sistemanf.entidades.SequenciaNota;
import com.nota.sistemanf.repository.ClienteRepository;
import com.nota.sistemanf.repository.ItemRepository;
import com.nota.sistemanf.repository.NotaRepository;
import com.nota.sistemanf.repository.ProdutoRepository;
import com.nota.sistemanf.repository.SequenciaNotaRepository;

@Service
public class NotaService {

	@Autowired
	private ItemRepository itemRepo;

	@Autowired
	private NotaRepository notaRepo;

	@Autowired
	private ClienteRepository clienteRepo;

	@Autowired
	private ProdutoRepository produtoRepo;

	@Autowired
	private SequenciaNotaRepository sequenciaNotaRepo;

	@Transactional
	public ResponseEntity<?> cadastrar(NotaFORM notaForm) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

		if (!clienteRepo.existsById(notaForm.getIdCliente())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Não existe cliente cadastrado para o id: " + notaForm.getIdCliente());
		}
		Cliente cliente = clienteRepo.findById(notaForm.getIdCliente()).get();

		Nota nota = new Nota();
		nota.setCliente(cliente);
		this.defineNumeroNota(nota);
		Nota notaSalva = notaRepo.save(nota);

		List<ItemFORM> itensForm = notaForm.getItens();

		BigDecimal somaValorItens = BigDecimal.ZERO;

		for (int i = 0; i < itensForm.size(); i++) {
			ItemFORM itemF = itensForm.get(i);

			if (!produtoRepo.existsById(itemF.getIdProduto())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Não existe produto cadastrado para o id: " + itemF.getIdProduto());
			}
			Produto produto = produtoRepo.findById(itemF.getIdProduto()).get();
			Item item = new Item();
			item.setProduto(produto);
			item.setQuantidade(itemF.getQuantidade());
			item.atualizaValorTotal();
			item.setNota(notaSalva);
			itemRepo.save(item);

			somaValorItens = somaValorItens.add(item.getValorTotal());
		}

		notaSalva.setValorTotal(somaValorItens);
		NotaDTO notaSalvaDTO = new NotaDTO(notaSalva);

		URI uri = uriBuilder.path("/snf/nota/{id}").buildAndExpand(notaSalva.getId()).toUri();
		return ResponseEntity.created(uri).body(notaSalvaDTO);
	}

	private void defineNumeroNota(Nota nota) {
		Integer ultimoNumero = sequenciaNotaRepo.ultimoNumero();
		nota.setNumero(ultimoNumero + 1);
		SequenciaNota sq = sequenciaNotaRepo.findByFuncao("INCREMENTO");
		sq.setUltimoNumeroNota(ultimoNumero + 1);
	}

	public ResponseEntity<?> listar() {

		List<Nota> notas = (List<Nota>) notaRepo.findAll();

		System.out.println("\n\n Tem item?" + notas.get(0).getItens() + "\n\n\n");

		if (notas == null || notas.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma nota cadastrada no banco!");
		}

		List<NotaDTO> notasDTO = notas.stream().map(NotaDTO::new).collect(Collectors.toList());

		return ResponseEntity.ok(notasDTO);
	}

}

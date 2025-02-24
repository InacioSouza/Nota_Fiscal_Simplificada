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

import com.nota.sistemanf.controller.dto.ClienteDTO;
import com.nota.sistemanf.controller.form.ClienteFORM;
import com.nota.sistemanf.entidades.Cliente;
import com.nota.sistemanf.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;

	@Transactional
	public ResponseEntity<?> cadastrar(ClienteFORM clienteForm) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

		if (clienteRepo.existsByNomeIgnoreCase(clienteForm.getNome())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente já cadastrado!");
		}
		Cliente clienteSalvo = clienteRepo.save(new Cliente(clienteForm));
		URI uri = uriBuilder.path("/snf/cliente/{id}").buildAndExpand(clienteSalvo.getId()).toUri();

		return ResponseEntity.created(uri).body(new ClienteDTO(clienteSalvo));
	}

	public ResponseEntity<?> listar() {
		List<Cliente> clientes = (List<Cliente>) clienteRepo.findAll();

		if (clientes == null || clientes.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum cliente cadastrado no banco!");
		}
		List<ClienteDTO> clientesDTO = clientes.stream().map(ClienteDTO::new).collect(Collectors.toList());

		return ResponseEntity.ok(clientesDTO);
	}

	public ResponseEntity<?> buscaClientePorID(Integer id) {
		if (!clienteRepo.existsById(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nenhum cliente encontrado para o id: " + id);
		}
		Cliente cliente = clienteRepo.findById(id).get();

		return ResponseEntity.ok(new ClienteDTO(cliente));
	}

	public ResponseEntity<?> buscaClienteporNome(String nome) {
		Cliente cliente = clienteRepo.findByNomeIgnoreCase(nome);

		if (cliente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("O cliente '" + nome + "' não está cadastrado no banco!");
		}
		return ResponseEntity.ok(new ClienteDTO(cliente));
	}

	@Transactional
	public ResponseEntity<?> atualizar(Integer id, ClienteFORM clienteAtualizado) {
		if (!clienteRepo.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe cliente cadastrado para o id: " + id);
		}
		Cliente cliente = clienteRepo.findById(id).get();
		cliente.setNome(clienteAtualizado.getNome());

		return ResponseEntity.ok(new ClienteDTO(cliente));
	}

	@Transactional
	public ResponseEntity<?> deletar(Integer id) {
		if (!clienteRepo.existsById(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não existe cliente cadastrado para o id: " + id);
		}
		if (this.qtdNotasVinculadas(id) != 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Não é possível deletar o cliente, existem notas vinculadas a ele!");
		}
		clienteRepo.deleteById(id);

		return ResponseEntity.ok("Cliente deletado!");
	}

	private Integer qtdNotasVinculadas(Integer id) {
		return clienteRepo.qtdNotasVinculadas(id);
	}
}

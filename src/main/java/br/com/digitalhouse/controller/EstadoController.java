package br.com.digitalhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.digitalhouse.controller.openapi.EstadoControllerOpenAPI;
import br.com.digitalhouse.model.Cidade;
import br.com.digitalhouse.model.Estado;
import br.com.digitalhouse.repository.EstadoRepository;
import br.com.digitalhouse.security.permissoes.CheckSecurity;

@CrossOrigin
@RestController
@RequestMapping("/estado")
public class EstadoController implements EstadoControllerOpenAPI{

	@Autowired
	private EstadoRepository repository;
	
	@PostMapping
	public void salvar(@RequestBody Estado estado) {
		repository.save(estado);
	}
	
	//@CheckSecurity.Estado.ListarEstados
	@GetMapping
	public List<Estado> listar(){
		return repository.findAll();
	}
	
	@CheckSecurity.Estado.ListarCidadesPorEstado
	@GetMapping("/{id}/cidades")
	public List<Cidade> listarCidadesPorEstado(@PathVariable Long id){
		return repository.buscarCidades(id);
	}
}
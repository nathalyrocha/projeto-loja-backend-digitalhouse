package br.com.digitalhouse.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digitalhouse.dto.ClienteDTO;
import br.com.digitalhouse.mapper.ClienteMapper;
import br.com.digitalhouse.model.Cliente;
import br.com.digitalhouse.model.Telefone;
import br.com.digitalhouse.repository.CidadeRepository;
import br.com.digitalhouse.repository.ClienteRepository;
import br.com.digitalhouse.repository.EstadoRepository;
import br.com.digitalhouse.request.ClienteRequest;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	public ClienteMapper mapper;

	@Transactional
	public ClienteDTO salvar(ClienteRequest request) {
		
		Cliente cliente = mapper.dtoRequestToModel(request);
		cliente.setDataNasc(LocalDate.now());
		
		if(cliente.getEndereco().getCidade().getId() == null) {
			estadoRepository.save(cliente.getEndereco().getCidade().getEstado());
		    cidadeRepository.save(cliente.getEndereco().getCidade());
		}
    
	    cliente.getTelefones().stream().
		forEach(telefone -> telefone.setCliente(cliente));	
			
	    return mapper.modelToDto(repository.save(cliente));		
	}

	@Transactional
	public void atualizar(Cliente cliente) {
		
		cliente.getTelefone().stream().forEach(telefone -> telefone.setCliente(cliente));
		
		repository.save(cliente);		
	}
	
	public Optional<Cliente> buscar(Long id) {
		return repository.findById(id);
	}
	
	public List<Cliente> listar() {
		return repository.findAll();
		
	}

	@Transactional
	public void excluir(Long id) {
		
		try {
			repository.deleteById(id);
			repository.flush();
		
		} catch (EmptyResultDataAccessException e) {
			
			throw new ClienteNaoEncontradodException(id);
		};			
	}

	@Transactional
	public Cliente atualizar(Cliente cliente, Long id) {
		Cliente cl = repository.findById(id).get();
		
		cl.setNome(cliente.getNome());
		cl.setSobrenome(cliente.getSobrenome());
		cl.setTelefone(cliente.getTelefone());
		cl.setCpf(cliente.getCpf());
		cl.setRg(cliente.getRg());
		cl.setEmail(cliente.getEmail());
		
		repository.save(cl);
		
		return cl;
	}

	public List<Cliente> retornar(String sobrenome) {
		return repository.findBySobrenome(sobrenome);
	}
	
	public List<Telefone> buscarTelefones(Long id) {
		return repository.buscarTelefonesPorId(id);
	}

}

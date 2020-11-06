package br.com.digitalhouse.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.digitalhouse.dto.ClienteDTO;
import br.com.digitalhouse.dto.ClienteResumoDTO;
import br.com.digitalhouse.email.EnvioEmailService;
import br.com.digitalhouse.email.Mensagem;
import br.com.digitalhouse.exception.ClienteNaoEncontradoException;
import br.com.digitalhouse.mapper.ClienteMapper;
import br.com.digitalhouse.model.Cidade;
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
	private ClienteMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EnvioEmailService envioEmail;

	@Transactional
	public ClienteDTO salvar(ClienteRequest clienteRequest) {
		
//		Usuario usuario = new Usuario();
//		
//		usuario.setSenha(passwordEncoder.encode(clienteRequest.getSenha()));
//		
//		Grupo grupo = grupoRepository.findById((long)1).get();		
//		usuario.setGrupos((Set<Grupo>) Arrays.asList(grupo));
		
		
		Cliente cliente = mapper.requestToModel(clienteRequest);
		cliente.setDataNasc(LocalDate.now());
		
				
		if(cliente.getEndereco().getCidade().getId() == null) {
			estadoRepository.save(cliente.getEndereco().getCidade().getEstado());
		    cidadeRepository.save(cliente.getEndereco().getCidade());
		}
    
	    cliente.getTelefones().stream().
		forEach(telefone -> telefone.setCliente(cliente));	 
	    
	    
	    return mapper.modelToDTO( repository.save(cliente) );		
	}

	@Transactional
	public void atualizar(Cliente cliente) {
		
	    cliente.getTelefones().stream().
		forEach(telefone -> telefone.setCliente(cliente));	
	    
	    Cidade cidade = cidadeRepository.findById(cliente.getEndereco().getCidade().getId()).get();
	   
	    Mensagem mensagem = Mensagem.builder()
				.assunto(cliente.getNome() + " - Cliente atualizado")
				.corpo("cliente-atualizado.html")
				.variavel("cliente", cliente)
				.variavel("cidade", cidade)
				//.variavel("estado", cidade.getEstado().getNome())
				.destinatario(cliente.getEmail())
				.build();
		
		envioEmail.enviar(mensagem);
	    
		repository.save(cliente);		
	}
	
	public Optional<Cliente> buscar(Long id) {
		return repository.findById(id);
	}

	@Transactional
	public void excluir(Long id) {
		
		try {
			repository.deleteById(id);
			repository.flush();
		
		} catch (EmptyResultDataAccessException e) {
			throw new ClienteNaoEncontradoException(id);
		};			
	}

	public List<Telefone> buscarTelefones(Long id) {
		return repository.buscarTelefonesPorId(id);
	}

	public List<ClienteDTO> listar() {
		
		return repository.findAll()
				.stream()
				.map(cli -> mapper.modelToDTO(cli))
				.collect(Collectors.toList());	
	}

	public List<ClienteResumoDTO> listarResumo() {
		
		List<Cliente> clientes = repository.findAll();
			
		return clientes
				.stream()
				.map(cli -> mapper.modelToDtoResumo(cli))
				.collect(Collectors.toList());

	}
	
}
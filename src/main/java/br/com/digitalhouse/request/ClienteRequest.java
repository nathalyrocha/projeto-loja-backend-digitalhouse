package br.com.digitalhouse.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.digitalhouse.model.Endereco;
import br.com.digitalhouse.model.Telefone;
import lombok.Data;

@Data
public class ClienteRequest {

	private Long id;
	
	private String nome;	
	
	private String sobrenome;	
	
	private List<Telefone> telefones;
	
	@NotNull
	private String cpf;	
	
	@NotBlank
	private String rg;
	
	@Email
	private String email;
	
	private Endereco endereco;
}
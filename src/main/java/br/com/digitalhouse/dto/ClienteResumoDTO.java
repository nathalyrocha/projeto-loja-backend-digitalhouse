package br.com.digitalhouse.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class ClienteResumoDTO {
	
	private Long id;
	
	private String nome;
	
	private String sobrenome;
	
	private String email;
	
	@JsonIgnoreProperties("cidade")
	private String endereco;
}

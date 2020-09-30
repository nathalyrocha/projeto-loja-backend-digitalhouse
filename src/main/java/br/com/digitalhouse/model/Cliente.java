package br.com.digitalhouse.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String sobrenome;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Telefone> telefone;
	
	@NotNull
	@Column
	private String cpf;
	
	@NotBlank
	@Column
	private String rg;
	
	@Column
	private String email;
	
	@Embedded
	private Endereco endereco;

	public Cliente orElse(Object object) {
		return null;
	}
	
}

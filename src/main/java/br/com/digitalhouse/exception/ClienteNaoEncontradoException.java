package br.com.digitalhouse.exception;

public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public ClienteNaoEncontradoException(String mensagem) {
		super(mensagem);		
	}
	
	public ClienteNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro com código %d", id));
	}
}

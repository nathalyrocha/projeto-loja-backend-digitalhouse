package br.com.digitalhouse.exception;

public class ImagemNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public ImagemNaoEncontradaException(String mensagem) {
		super(mensagem);		
	}
	
	public ImagemNaoEncontradaException(Long id) {
		this(String.format("Não existe uma imagem de cliente com código %d", id));
	}
}

package br.com.sistemaescolar.importation;

public class DadosInvalidosException extends Exception {
	private static final long serialVersionUID = -6758888409912621556L;

	public DadosInvalidosException(String mensagem) {
		super(mensagem);
	}
}

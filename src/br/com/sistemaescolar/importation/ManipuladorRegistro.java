package br.com.sistemaescolar.importation;

public abstract class ManipuladorRegistro {
	protected void dispararErro(String mensagem) throws DadosInvalidosException {
		throw new DadosInvalidosException(mensagem);
	}
	
	protected Integer converterFaseParaInteiro(String fase) {
		return Integer.parseInt(fase.substring(0, 2));
	}
}

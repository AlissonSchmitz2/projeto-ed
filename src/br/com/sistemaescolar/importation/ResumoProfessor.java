package br.com.sistemaescolar.importation;

public class ResumoProfessor implements RegistroDadosInterface {
	public static final Integer CODIGO_REGISTRO = 3;
	private String nome;
	private String codigoTitulo;
	
	public ResumoProfessor(String nome, String codigoTitulo) {
		this.nome = nome;
		this.codigoTitulo = codigoTitulo;
	}

	public String getNome() {
		return nome;
	}

	public String getCodigoTitulo() {
		return codigoTitulo;
	}

	public boolean validar() throws DadosInvalidosException {
		return true;
	}
	
	public String toString() {
		return nome;
	}
}

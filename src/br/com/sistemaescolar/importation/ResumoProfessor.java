package br.com.sistemaescolar.importation;

import br.com.sistemaescolar.model.Configuracoes;

public class ResumoProfessor extends ManipuladorRegistro implements RegistroDadoInterface {
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

	public boolean validar(Configuracoes configuracoes) throws DadosInvalidosException {
		//É esperado que todas as informações do professor estejam presentes
		if (getNome() == null || getCodigoTitulo() == null) {
			dispararErro("Existe um ou mais dados faltantes do PROFESSOR");
		}
				
		return true;
	}
	
	public String toString() {
		return nome;
	}
}

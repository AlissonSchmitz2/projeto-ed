package br.com.sistemaescolar.importation;

import br.com.sistemaescolar.model.Configuracoes;

public class TrailerArquivo extends ManipuladorRegistro implements RegistroDadoInterface {
	public static final Integer CODIGO_REGISTRO = 9;
	private Integer totalRegistros;
	
	public TrailerArquivo(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	
	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	public boolean validar(Configuracoes configuracoes) throws DadosInvalidosException {
		if (getTotalRegistros() == null) {
			//É esperado que total de registro esteja presente no trailer
			dispararErro("O total de registro não está presente no TRAILER");
		}
		
		return true;
	}
}

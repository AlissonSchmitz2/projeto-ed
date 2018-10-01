package br.com.sistemaescolar.importation;

public class TrailerArquivo implements RegistroDadosInterface {
	public static final Integer CODIGO_REGISTRO = 9;
	private Integer totalRegistros;
	
	public TrailerArquivo(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	
	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	public boolean validar() throws DadosInvalidosException {
		if (getTotalRegistros() == null) {
			//É esperado que total de registro esteja presente no trailer
			throw new DadosInvalidosException("O TRAILER do arquivo é inválido");
		}
		
		return true;
	}
}

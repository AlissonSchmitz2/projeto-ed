package br.com.sistemaescolar.importation;

import java.util.Date;

public class HeaderArquivo implements RegistroDadosInterface {
	public static final Integer CODIGO_REGISTRO = 0;
	private String nomeCurso;
	private Date dataProcessamento;
	private String faseInicial;
	private String faseFinal;
	private Integer numeroSequencial;
	private String versaoLayout;
	
	public HeaderArquivo(String nomeCurso, Date dataProcessamento, String faseInicial, String faseFinal, Integer numeroSequencial, String versaoLayout) {
		this.nomeCurso = nomeCurso;
		this.dataProcessamento = dataProcessamento;
		this.faseInicial = faseInicial;
		this.faseFinal = faseFinal;
		this.numeroSequencial = numeroSequencial;
		this.versaoLayout = versaoLayout;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}
	
	public Date getDataProcessamento() {
		return dataProcessamento;
	}
	
	public String getFaseInicial() {
		return faseInicial;
	}
	
	public String getFaseFinal() {
		return faseFinal;
	}
	
	public Integer getNumeroSequencial() {
		return numeroSequencial;
	}
	
	public String getVersaoLayout() {
		return versaoLayout;
	}

	public boolean validar() throws DadosInvalidosException {
		if (getNomeCurso() == null || getDataProcessamento() == null || getFaseInicial() == null || getFaseFinal() == null || getNumeroSequencial() == null || getVersaoLayout() == null) {
			//É esperado que todas as informações do header estejam presentes
			throw new DadosInvalidosException("O HEADER do arquivo é inválido");
		}
		
		//TODO: validar o sequencial (Deve ser o próximo número imediato ao guardado no controle)]
		//TODO: validar se a fase inicial é menor ou igual a fase final
		
		return true;
	}
}

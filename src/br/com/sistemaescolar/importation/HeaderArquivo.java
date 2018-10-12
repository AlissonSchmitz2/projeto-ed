package br.com.sistemaescolar.importation;

import java.util.Date;

import br.com.sistemaescolar.model.Configuracoes;

public class HeaderArquivo extends ManipuladorRegistro implements RegistroDadoInterface {
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

	public boolean validar(Configuracoes configuracoes) throws DadosInvalidosException {
		//É esperado que todas as informações do header estejam presentes
		if (getNomeCurso() == null || getDataProcessamento() == null || getFaseInicial() == null || getFaseFinal() == null || getNumeroSequencial() == null || getVersaoLayout() == null) {
			dispararErro("Existe um ou mais dados faltantes do HEADER do arquivo");
		}
		
		//Sequêncial deve ser igual ao armazenado nas configurações
		if (configuracoes.getSequencialImportacao().compareTo(getNumeroSequencial()) != 0) {
			dispararErro("O número sequência contido no HEADER é inválido: Espera-se que o sequencial seja " + configuracoes.getSequencialImportacao());
		}
		
		//Verifica o valor de fase inicial é válido
		if (converterFaseParaInteiro(getFaseInicial()).compareTo(converterFaseParaInteiro(getFaseFinal())) == 1) {
			dispararErro("As fases contidas no HEADER são inválidas: Espera-se que a fase inicial \"" + getFaseInicial() + "\" seja menor ou igual a fase final \"" + getFaseFinal() + "\"");
		}
		
		return true;
	}
}

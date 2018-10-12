package br.com.sistemaescolar.importation;

import java.util.HashMap;
import java.util.Map;

import br.com.sistemaescolar.model.Configuracoes;

public class ResumoFase extends ManipuladorRegistro implements RegistroDadoInterface {
	public static final Integer CODIGO_REGISTRO = 1;
	private String fase;
	private Integer qtdeDisciplinasEsperadas; //Quantidade de disciplinas baseado nas informações do arquivo de importação
	private Integer qtdeProfessoresEsperados; //Quantidade de professores baseado nas informações do arquivo de importação
	
	private Map<String, ResumoDisciplina> disciplinasParaImportar = new HashMap<String, ResumoDisciplina>();
	
	public ResumoFase(String fase, Integer qtdeDisciplinasEsperadas, Integer qtdeProfessoresEsperados) {
		this.fase = fase;
		this.qtdeDisciplinasEsperadas = qtdeDisciplinasEsperadas;
		this.qtdeProfessoresEsperados = qtdeProfessoresEsperados;
	}
	
	public String getFase() {
		return fase;
	}
	
	public Integer getQtdeDisciplinasEsperadas() {
		return qtdeDisciplinasEsperadas;
	}
	
	public Integer getQtdeProfessoresEsperados() {
		return qtdeProfessoresEsperados;
	}
	
	public void inserirDisciplina(ResumoDisciplina disciplina) {
		disciplinasParaImportar.put(disciplina.getCodigoDisciplina(), disciplina);
	}
	
	public Map<String, ResumoDisciplina> getDisciplinas() {
		return disciplinasParaImportar;
	}
	
	public boolean validar(Configuracoes configuracoes) throws DadosInvalidosException {
		//É esperado que todas as informações do fase estejam presentes
		if (getFase() == null || getQtdeDisciplinasEsperadas() == null || getQtdeProfessoresEsperados() == null) {
			dispararErro("Existe um ou mais dados faltantes do FASE");
		}
		
		//Espera-se que a quantidade de disciplinas extraída é a mesma informada no arquivo
		if (getQtdeDisciplinasEsperadas().compareTo(getDisciplinas().size()) != 0) {
			dispararErro(getFase() + " - A quantidade de disciplinas esperadas \"" + getQtdeDisciplinasEsperadas() + "\" é "
					+ "diferente da quantidade encontrada \"" + getDisciplinas().size() +"\"");
		}

		try {
			Integer totalProfessores = 0;
			
			//Itera sobre cada um dos resumo disciplina
			for (ResumoDisciplina resumoDisciplina : getDisciplinas().values()){
				//Valida cada um dos resumos de disciplina
				resumoDisciplina.validar(configuracoes);
				
				totalProfessores += resumoDisciplina.getProfessores().size();
			}
			
			//É esperado que a quantidade de professores esperadaseja a mesma quantidade extraída
			if (getQtdeProfessoresEsperados().compareTo(totalProfessores) != 0) {
				dispararErro("A quantidade de professores esperados \"" + getQtdeProfessoresEsperados() + "\" é "
						+ "diferente da quantidade encontrada \"" + totalProfessores +"\"");
			}
		} catch (Exception e) {
			//Intercepta o erro para adicionar a fase a mensagem
			dispararErro(getFase() + " - " + e.getMessage());
		}
		
		return true;
	}
	
	public String toString() {
      return fase;
    }
}

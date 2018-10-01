package br.com.sistemaescolar.importation;

import java.util.HashMap;
import java.util.Map;

public class ResumoFase implements RegistroDadosInterface {
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
	
	public boolean validar() throws DadosInvalidosException {
		return true;
	}
	
	public String toString() {
      return fase;
    }
}

package br.com.sistemaescolar.importation;

import java.util.HashMap;
import java.util.Map;

public class ResumoDisciplina implements RegistroDadosInterface {
	public static final Integer CODIGO_REGISTRO = 2;
	private String codigoDisciplina;
	private String nomeDisciplina;
	private String codigoDiaSemana;
	private Integer qtdeProfessoresEsperados; //Quantidade de professores baseado nas informações do arquivo de importação
	
	private Map<String, ResumoProfessor> professoresParaImportar = new HashMap<String, ResumoProfessor>();
	
	public ResumoDisciplina(String codigoDisciplina, String nomeDisciplina, String codigoDiaSemana, Integer qtdeProfessoresEsperados) {
		this.codigoDisciplina = codigoDisciplina;
		this.nomeDisciplina = nomeDisciplina;
		this.codigoDiaSemana = codigoDiaSemana;
		this.qtdeProfessoresEsperados = qtdeProfessoresEsperados;
	}
	
	public String getCodigoDisciplina() {
		return codigoDisciplina;
	}
	
	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}
	
	public String getNomeDisciplina() {
		return nomeDisciplina;
	}
	
	public String getCodigoDiaSemana() {
		return codigoDiaSemana;
	}
	
	public Integer getQtdeProfessoresEsperados() {
		return qtdeProfessoresEsperados;
	}
	
	public void inserirProfessor(ResumoProfessor professor) {
		professoresParaImportar.put(professor.getNome(), professor);
	}
	
	public Map<String, ResumoProfessor> getProfessores() {
		return professoresParaImportar;
	}
	
	public boolean validar() throws DadosInvalidosException {
		return true;
	}
	
	public String toString() {
		return codigoDisciplina + " - " + (nomeDisciplina != null ? nomeDisciplina : "Não cadastro");
	}
}

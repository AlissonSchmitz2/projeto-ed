package br.com.sistemaescolar.model;

public class Grade {
	
	Integer id;
	private Integer id_fase;
	private Integer id_disciplina;
	private Integer id_professor;
	
	private String descricaoFase;
	private String descricaoDisciplina;
	private String descricaoProfessor;
	private String descricaoCurso;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId_fase() {
		return id_fase;
	}
	public void setId_fase(Integer id_fase) {
		this.id_fase = id_fase;
	}
	public Integer getId_disciplina() {
		return id_disciplina;
	}
	public void setId_disciplina(Integer id_disciplina) {
		this.id_disciplina = id_disciplina;
	}
	public Integer getId_professor() {
		return id_professor;
	}
	public void setId_professor(Integer id_professor) {
		this.id_professor = id_professor;
	}
	public String getDescricaoFase() {
		return descricaoFase;
	}
	public void setDescricaoFase(String descricaoFase) {
		this.descricaoFase = descricaoFase;
	}
	public String getDescricaoDisciplina() {
		return descricaoDisciplina;
	}
	public void setDescricaoDisciplina(String descricaoDisciplina) {
		this.descricaoDisciplina = descricaoDisciplina;
	}
	public String getDescricaoProfessor() {
		return descricaoProfessor;
	}
	public void setDescricaoProfessor(String descricaoProfessor) {
		this.descricaoProfessor = descricaoProfessor;
	}
	public String getDescricaoCurso() {
		return descricaoCurso;
	}
	public void setDescricaoCurso(String descricaoCurso) {
		this.descricaoCurso = descricaoCurso;
	}
	
	
	
}

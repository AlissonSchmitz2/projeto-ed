package br.com.sistemaescolar.model;

public class Grade {
	
	Integer id;
	private Integer id_fase;
	private Integer id_disciplina;
	private Integer id_professor;
	
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
	
}

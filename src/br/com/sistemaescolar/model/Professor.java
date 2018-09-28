package br.com.sistemaescolar.model;

public class Professor {
	Integer id;
	Integer idDisciplina;

	private String professor;

	public Professor(Integer id, String professor) {
		this.id = id;
		this.professor = professor;
	}

	public Professor() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public Integer getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(Integer idDisciplina) {
		this.idDisciplina = idDisciplina;
	}
}

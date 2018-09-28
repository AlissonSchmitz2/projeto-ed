package br.com.sistemaescolar.model;

public class Curso {
	Integer id;
	private String curso;
	
	public Curso() {
		
	}

	public Curso(Integer id, String curso) {
		this.id = id;
		this.curso = curso;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}	
}

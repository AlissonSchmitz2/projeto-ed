package br.com.sistemaescolar.model;

public class Professor {
	Integer id;
	private String professor;
	private String tituloDocente;

	public Professor(Integer id, String professor, String tituloDocente) {
		this.id = id;
		this.professor = professor;
		this.tituloDocente = tituloDocente;
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

	public String getTituloDocente() {
		return tituloDocente;
	}

	public void setTituloDocente(String tituloDocente) {
		this.tituloDocente = tituloDocente;
	}
	
	
}

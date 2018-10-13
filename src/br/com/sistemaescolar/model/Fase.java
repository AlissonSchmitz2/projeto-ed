package br.com.sistemaescolar.model;

public class Fase {

	Integer id;
	private Curso curso;
	private String nome;

	public Fase(Integer id, String nome, Curso curso) {
		this.id = id;
		this.nome = nome;
		this.curso = curso;
	}
	
	public Fase() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}

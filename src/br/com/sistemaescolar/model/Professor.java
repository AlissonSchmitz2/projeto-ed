package br.com.sistemaescolar.model;

public class Professor {
	private Integer id;
	private String nome;
	private String tituloDocente;

	public Professor(Integer id, String professor, String tituloDocente) {
		this.id = id;
		this.nome = professor;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTituloDocente() {
		return tituloDocente;
	}

	public void setTituloDocente(String tituloDocente) {
		this.tituloDocente = tituloDocente;
	}
}

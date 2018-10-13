package br.com.sistemaescolar.model;

public class Disciplina {
	private Integer id;
	private String nome;
	private Integer codDisciplina;

	public Disciplina(Integer id, int codDisciplina, String disciplina) {
		this.id = id;
		this.nome = disciplina;
		this.codDisciplina = codDisciplina;
	}

	public Disciplina() {

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

	public Integer getCodDisciplina() {
		return codDisciplina;
	}

	public void setCodDisciplina(int codDisciplina) {
		this.codDisciplina = codDisciplina;
	}
}

package br.com.sistemaescolar.model;

public class MatriculaItem {
	private Integer id;
	private Matricula matricula;
	private Fase fase;
	private Disciplina disciplina;
	private Curso curso;
	
	public MatriculaItem(Integer id, Matricula matricula, Curso curso, Fase fase, Disciplina disciplina) {
		this.id = id;
		this.curso = curso;
		this.disciplina = disciplina;
		this.fase = fase;
		this.matricula = matricula;
	}
	
	public MatriculaItem() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}

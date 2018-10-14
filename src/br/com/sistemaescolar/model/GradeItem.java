package br.com.sistemaescolar.model;

public class GradeItem {
	private Integer id;
	private Grade grade;
	private Disciplina disciplina;
	private Professor professor;
	private String codigoDiaSemana = "";
	
	public GradeItem(Integer id, Grade grade, Disciplina disciplina, Professor professor, String codigoDiaSemana) {
		this.id = id;
		this.grade = grade;
		this.disciplina = disciplina;
		this.professor = professor;
		this.codigoDiaSemana = codigoDiaSemana;
	}
	
	public GradeItem() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public String getCodigoDiaSemana() {
		return codigoDiaSemana;
	}

	public void setCodigoDiaSemana(String codigoDiaSemana) {
		this.codigoDiaSemana = codigoDiaSemana;
	}
}

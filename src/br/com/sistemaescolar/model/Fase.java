package br.com.sistemaescolar.model;

public class Fase {

	Integer id;
	private Integer idCurso;
	private String fase;

	public Fase(Integer id, String fase, Integer idCurso) {
		this.id = id;
		this.fase = fase;
		this.idCurso = idCurso;
	}
	
	public Fase() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public Integer getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

}

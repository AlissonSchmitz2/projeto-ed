package br.com.sistemaescolar.model;

import java.util.HashSet;

public class Grade {
	private Integer id;
	
	private Fase fase;
	
	private HashSet<GradeItem> itens = new HashSet<GradeItem>();
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Fase getFase() {
		return fase;
	}
	
	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public HashSet<GradeItem> getItens() {
		return itens;
	}

	public void setItens(HashSet<GradeItem> itens) {
		this.itens = itens;
	}
	
	public void setItem(GradeItem item) {
		itens.add(item);
	}
	
	public void clearItens() {
		itens.clear();
	}
}

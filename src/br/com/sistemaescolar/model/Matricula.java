package br.com.sistemaescolar.model;

import java.util.HashSet;

public class Matricula {
	
	private Integer id;
	private Aluno aluno;
	private String data;
	private int diaVencimento;
	
	private HashSet<MatriculaItem> itens = new HashSet<MatriculaItem>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer idMatricula) {
		this.id = idMatricula;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getDiaVencimento() {
		return diaVencimento;
	}

	public void setDiaVencimento(int diaVencimento) {
		this.diaVencimento = diaVencimento;
	}
	
	public HashSet<MatriculaItem> getItens() {
		return itens;
	}

	public void setItens(HashSet<MatriculaItem> itens) {
		this.itens = itens;
	}
	
	public void setItem(MatriculaItem item) {
		itens.add(item);
	}
	
	public void clearItens() {
		itens.clear();
	}
}

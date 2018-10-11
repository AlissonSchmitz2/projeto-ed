package br.com.sistemaescolar.model;

public class Configuracoes {
	Integer id;
	Integer sequencialImportacao;
	
	public Configuracoes(Integer id, Integer sequencialImportacao) {
		setId(id);
		setSequencialImportacao(sequencialImportacao);
	}

	public Configuracoes() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSequencialImportacao() {
		return sequencialImportacao != null ? sequencialImportacao : 0;
	}

	public void setSequencialImportacao(Integer sequencialImportacao) {
		this.sequencialImportacao = sequencialImportacao;
	}
	
	public void incrementarSequencialImportacao() {
		setSequencialImportacao(getSequencialImportacao() + 1);
	}
}

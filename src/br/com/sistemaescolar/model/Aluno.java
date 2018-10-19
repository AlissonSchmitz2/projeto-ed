package br.com.sistemaescolar.model;

public class Aluno implements Model {
	
	private Integer id;
	private String codAluno;
	private String numero;
	private String sexo;
	private String senhaAluno;
	private String dataNascimento;
	private String telefone;
	private String celular;
	private String email;
	private String observacao;
	private String complemento;
	private String nomeAluno;
	private String bairro;
	private String cidade;
	private String uf;
	private String pais;
	private String cep;
	private String endereco;
	
	public Aluno() {
		
	}
	
	public Aluno(Integer id, String codAluno, String numero, String sexo, String senhaAluno, String dataNascimento,
			String telefone, String celular, String email, String observacao, String complemento, String nomeAluno,
			String bairro, String cidade, String uf, String pais, String cep, String endereco) {
		super();
		this.id = id;
		this.codAluno = codAluno;
		this.numero = numero;
		this.sexo = sexo;
		this.senhaAluno = senhaAluno;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.celular = celular;
		this.email = email;
		this.observacao = observacao;
		this.complemento = complemento;
		this.nomeAluno = nomeAluno;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.pais = pais;
		this.cep = cep;
		this.endereco = endereco;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCodAluno() {
		return codAluno;
	}

	public void setCodAluno(String codAluno) {
		this.codAluno = codAluno;
	}

	public String getSenhaAluno() {
		return senhaAluno;
	}

	public void setSenhaAluno(String senhaAluno) {
		this.senhaAluno = senhaAluno;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
}

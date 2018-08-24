package model;

public class Usuario {
	private String senha;
	private String login;
	private String perfil;
	
	public Usuario() {
	}
	
	public Usuario(String login, String senha, String perfil) {
		this.senha = senha;
		this.login = login;
		this.perfil = perfil;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
}

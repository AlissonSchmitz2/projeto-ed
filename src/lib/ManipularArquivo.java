package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.Aluno;
import model.Cidade;
import model.Usuario;

public class ManipularArquivo {

	private static String USUARIOS_PATH = ".\\src\\data\\usuarios.txt";
	private static String CIDADES_PATH = ".\\src\\data\\cidades.txt";
	private static String ALUNOS_PATH = ".\\src\\data\\alunos.txt";
	
	private BufferedReader lerArq;

	public ManipularArquivo() {
	}

	public void inserirDado(Cidade cidade) throws IOException {
		FileWriter arq = new FileWriter(getDestinoArquivo("cidades"), true);
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.println(cidade.getCidade() + "," + cidade.getUf() + "," + cidade.getPais());
		arq.close();
	}

	public void inserirDado(Usuario usuario) throws IOException {
		FileWriter arq = new FileWriter(getDestinoArquivo("usuarios"), true);
		PrintWriter gravarArq = new PrintWriter(arq);

		gravarArq.println(usuario.getLogin() + "," + usuario.getSenha() + "," + usuario.getPerfil());
		arq.close();
	}

	public void inserirDado(Aluno aluno) throws IOException {
		FileWriter arq = new FileWriter(getDestinoArquivo("alunos"), true);
		PrintWriter gravarArq = new PrintWriter(arq);

		gravarArq.println(aluno.getCodAluno() + "," + aluno.getNomeAluno() + "," + aluno.getSexo() + ","
				+ aluno.getDataNascimento() + "," + aluno.getTelefone() + "," + aluno.getCelular() + ","
				+ aluno.getEmail() + "," + aluno.getObservacao() + "," + aluno.getEndereco() + ","
				+ aluno.getComplemento() + "," + aluno.getCep() + "," + aluno.getBairro() + "," + aluno.getCidade()
				+ "," + aluno.getUf() + "," + aluno.getPais());
		arq.close();
	}
	
	public Usuario getUsuarioByCodigoSenha(String codigo, String senha) throws Exception {
		try {
			FileReader arq = new FileReader(getDestinoArquivo("usuarios"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			
			//Se a linha estiver vazia (Nenhum usuário cadastrado)
			if (linha == null) {
				throw new Exception("Nenhum usuário cadastrado");
			}
			
			while (linha != null) {
				String[] verificaLinha = linha.split(",");
				
				if (codigo.equals(verificaLinha[0]) && senha.equals(verificaLinha[1])) {
					return new Usuario();
				}
				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		
		return null;
	}
	
	private String getDestinoArquivo(String area) {
		switch (area) {
			case "usuarios":
				return new File(USUARIOS_PATH).getAbsolutePath();
			case "cidades":
				return new File(CIDADES_PATH).getAbsolutePath();
			case "alunos":
				return new File(ALUNOS_PATH).getAbsolutePath();
		}
		
		return null;
	}
}

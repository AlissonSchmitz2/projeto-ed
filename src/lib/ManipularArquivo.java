package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.Alunos;
import model.Cidade;
import model.Usuario;

public class ManipularArquivo {

	private BufferedReader lerArq;

	public ManipularArquivo() {

	}

	public void inserirDado(Cidade cidade) throws IOException {
		String destino = new File(".\\src\\data\\cidades.txt").getAbsolutePath();
		FileWriter arq = new FileWriter(destino, true);
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.println(cidade.getCidade() + "," + cidade.getUf() + "," + cidade.getPais());
		arq.close();
	}

	public void inserirDado(Usuario usuario) throws IOException {
		String destino = new File(".\\src\\data\\usuarios.txt").getAbsolutePath();
		FileWriter arq = new FileWriter(destino, true);
		PrintWriter gravarArq = new PrintWriter(arq);

		gravarArq.println(usuario.getNomeUsuario() + "," + usuario.getSenha() + "," + usuario.getPerfil());
		arq.close();
	}

	public void inserirDado(Alunos aluno) throws IOException {
		String destino = new File(".\\src\\data\\alunos.txt").getAbsolutePath();
		FileWriter arq = new FileWriter(destino, true);
		PrintWriter gravarArq = new PrintWriter(arq);

		gravarArq.println(aluno.getCodAluno() + "," + aluno.getNomeAluno() + "," + aluno.getSexo() + ","
				+ aluno.getDataNascimento() + "," + aluno.getTelefone() + "," + aluno.getCelular() + ","
				+ aluno.getEmail() + "," + aluno.getObservacao() + "," + aluno.getEndereco() + ","
				+ aluno.getComplemento() + "," + aluno.getCep() + "," + aluno.getBairro() + "," + aluno.getCidade()
				+ "," + aluno.getUf() + "," + aluno.getPais());
		arq.close();
	}
	public String lerArquivo(String destino, String login, String senha) {
		try {
			FileReader arq = new FileReader(destino);
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			if (linha == null) {
				return "CadastraAdministrador";
			}
			while (linha != null) {
				String[] verificaLinha = linha.split(",");

				if (login.equals(verificaLinha[0]) && senha.equals(verificaLinha[1])
						&& (verificaLinha[2].equals("Administrador"))) {
					return "Administrador";
				} else if (login.equals(verificaLinha[0]) && senha.equals(verificaLinha[1])
						&& (verificaLinha[2].equals("Convidado"))) {
					return "Convidado";
				}
				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		return "Incorreto";
	}
}

package lib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.Cidade;
import model.Usuario;

public class ArquivoManipular {

	private BufferedReader lerArq;

	public ArquivoManipular() {

	}

	public void criarArquivo(Cidade cidade) throws IOException {
		FileWriter arq = new FileWriter(
				"C:\\Users\\PC Magazine\\eclipse-workspace\\SistemaEscolar\\src\\view\\cadastroCidades\\cadastro.txt",
				true);
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.println(cidade.getCidade() + "," + cidade.getUf() + "," + cidade.getPais());
		arq.close();
	}

	public void criarArquivo(Usuario usuario) throws IOException {
		FileWriter arq = new FileWriter(
				"C:\\Users\\PC Magazine\\eclipse-workspace\\SistemaEscolar\\src\\view\\cadastro\\cadastro.txt", true);
		PrintWriter gravarArq = new PrintWriter(arq);

		gravarArq.println(usuario.getNomeUsuario() + "," + usuario.getSenha() + "," + usuario.getPerfil());
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

package lib;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import model.Cidade;
import model.Usuario;

public class ManipularArquivo {

	private static String USUARIOS_PATH = ".\\src\\data\\usuarios.txt";
	private static String CIDADES_PATH = ".\\src\\data\\cidades.txt";
	private static String ALUNOS_PATH = ".\\src\\data\\alunos.txt";
	
	private static String SEPARATOR = ",";
	
	private BufferedReader lerArq;

	public ManipularArquivo() {
	}

	/*
	 * USUÁRIOS
	 */
	public void inserirDado(Usuario usuario) throws IOException {
		usuario.setId(pegarProximoId("usuarios"));

		inserirDadosNoArquivo("usuarios", usuario.getId() + SEPARATOR + usuario.getLogin() + SEPARATOR + usuario.getSenha() + SEPARATOR + usuario.getPerfil());
	}
	
	public void editarDado(Usuario usuario) {
		try {
			FileReader arq = new FileReader(getDestinoArquivo("usuarios"));
			lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			StringBuffer inputBuffer = new StringBuffer();
			
			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);
				
				if (usuario.getId().toString().equals(atributo[0])) {
					linha = usuario.getId() + SEPARATOR + usuario.getLogin() + SEPARATOR + usuario.getSenha() + SEPARATOR + usuario.getPerfil();
				}
				
				inputBuffer.append(linha);
	            inputBuffer.append('\n');
				
				linha = lerArq.readLine();
	        }
			
	        String inputStr = inputBuffer.toString();
	        
	        lerArq.close();
			
	        FileOutputStream fileOut = new FileOutputStream(getDestinoArquivo("usuarios"));
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}
	
	public void removerDado(Usuario usuario) {
		try {
			FileReader arq = new FileReader(getDestinoArquivo("usuarios"));
			lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			StringBuffer inputBuffer = new StringBuffer();
			
			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);
				
				if (!usuario.getId().toString().equals(atributo[0])) {
					inputBuffer.append(linha);
		            inputBuffer.append('\n');
				}
				
				linha = lerArq.readLine();
	        }
			
	        String inputStr = inputBuffer.toString();
	        
	        lerArq.close();
			
	        FileOutputStream fileOut = new FileOutputStream(getDestinoArquivo("usuarios"));
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}
	
	public Usuario pegarUsuarioPorId(Integer id) {
		try {
			FileReader arq = new FileReader(getDestinoArquivo("usuarios"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			
			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);
				
				if (id.toString().equals(atributo[0])) {
					return criarUsuarioApartirAtributos(atributo);
				}
				
				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		
		return null;
	}
	
	public Usuario pegarUsuarioPorLoginSenha(String login, String senha) throws Exception {
		try {
			FileReader arq = new FileReader(getDestinoArquivo("usuarios"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			
			//Se a linha for null, significa que o arquivo de dados está vazio (Uma exeption é retornada)
			if (linha == null) {
				throw new Exception("Nenhum usuário cadastrado");
			}
			
			while (linha != null) {
				String[] verificaLinha = linha.split(SEPARATOR);
				
				if (login.equals(verificaLinha[1]) && senha.equals(verificaLinha[2])) {
					return new Usuario(Integer.parseInt(verificaLinha[0]), verificaLinha[1], verificaLinha[2], verificaLinha[3]);
				}
				
				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		
		return null;
	}
	
	public List<Usuario> pegarUsuarios() throws Exception {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			FileReader arq = new FileReader(getDestinoArquivo("usuarios"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			
			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);
				
				usuarios.add(criarUsuarioApartirAtributos(atributo));
				
				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		
		return usuarios;
	}
	
	private Usuario criarUsuarioApartirAtributos(String[] atributo) {
		Usuario novoUsuario = new Usuario();
		
		novoUsuario.setId(Integer.parseInt(atributo[0]));
		novoUsuario.setLogin(atributo[1]);
		novoUsuario.setSenha(atributo[2]);
		novoUsuario.setPerfil(atributo[3]);
		
		return novoUsuario;
	}
	
	/*
	 * ALUNOS
	 */
	public void inserirDado(Aluno aluno) throws IOException {
		aluno.setId(pegarProximoId("alunos"));

		inserirDadosNoArquivo("alunos", aluno.getId() + SEPARATOR + aluno.getCodAluno() + SEPARATOR + aluno.getNomeAluno() + SEPARATOR + aluno.getSexo() + SEPARATOR
				+ aluno.getDataNascimento() + SEPARATOR + aluno.getTelefone() + SEPARATOR + aluno.getCelular() + SEPARATOR
				+ aluno.getEmail() + SEPARATOR + aluno.getObservacao() + SEPARATOR + aluno.getEndereco() + SEPARATOR
				+ aluno.getComplemento() + SEPARATOR + aluno.getCep() + SEPARATOR + aluno.getBairro() + SEPARATOR + aluno.getCidade()
				+ SEPARATOR + aluno.getUf() + SEPARATOR + aluno.getPais());
		//TODO: falta salvar Número do endereço
	}
	
	public void editarDado(Aluno aluno) {
		try {
			FileReader arq = new FileReader(getDestinoArquivo("alunos"));
			lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			StringBuffer inputBuffer = new StringBuffer();
			
			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);
				
				if (aluno.getId().toString().equals(atributo[0])) {
					linha = aluno.getId() + SEPARATOR + aluno.getCodAluno() + SEPARATOR + aluno.getNomeAluno() + SEPARATOR + aluno.getSexo() + SEPARATOR
							+ aluno.getDataNascimento() + SEPARATOR + aluno.getTelefone() + SEPARATOR + aluno.getCelular() + SEPARATOR
							+ aluno.getEmail() + SEPARATOR + aluno.getObservacao() + SEPARATOR + aluno.getEndereco() + SEPARATOR
							+ aluno.getComplemento() + SEPARATOR + aluno.getCep() + SEPARATOR + aluno.getBairro() + SEPARATOR + aluno.getCidade()
							+ SEPARATOR + aluno.getUf() + SEPARATOR + aluno.getPais();
					//TODO: falta salvar Número do endereço
				}
				
				inputBuffer.append(linha);
	            inputBuffer.append('\n');
				
				linha = lerArq.readLine();
	        }
			
	        String inputStr = inputBuffer.toString();
	        
	        lerArq.close();
			
	        FileOutputStream fileOut = new FileOutputStream(getDestinoArquivo("alunos"));
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}
	
	public void removerDado(Aluno aluno) {
		try {
			FileReader arq = new FileReader(getDestinoArquivo("alunos"));
			lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			StringBuffer inputBuffer = new StringBuffer();
			
			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);
				
				if (!aluno.getId().toString().equals(atributo[0])) {
					inputBuffer.append(linha);
		            inputBuffer.append('\n');
				}
				
				linha = lerArq.readLine();
	        }
			
	        String inputStr = inputBuffer.toString();
	        
	        lerArq.close();
			
	        FileOutputStream fileOut = new FileOutputStream(getDestinoArquivo("alunos"));
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}
	
	public Aluno pegarAlunoPorId(Integer id) {
		try {
			FileReader arq = new FileReader(getDestinoArquivo("alunos"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			
			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);
				
				if (id.toString().equals(atributo[0])) {
					return criarAlunoApartirAtributos(atributo);
				}
				
				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		
		return null;
	}
	
	public List<Aluno> pegarAlunos() throws Exception {
		List<Aluno> alunos = new ArrayList<Aluno>();

		try {
			FileReader arq = new FileReader(getDestinoArquivo("alunos"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			
			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);
				
				alunos.add(criarAlunoApartirAtributos(atributo));
				
				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		
		return alunos;
	}

	private Aluno criarAlunoApartirAtributos(String[] atributo) {
		Aluno novoAluno = new Aluno();
		
		novoAluno.setId(Integer.parseInt(atributo[0]));
		novoAluno.setCodAluno(atributo[1]);
		novoAluno.setNomeAluno(atributo[2]);
		novoAluno.setSexo(atributo[3].charAt(0));
		novoAluno.setDataNascimento(atributo[4]);
		novoAluno.setTelefone(atributo[5]);
		novoAluno.setCelular(atributo[6]);
		novoAluno.setCep(atributo[11]);
		novoAluno.setEmail(atributo[7]);
		novoAluno.setObservacao(atributo[8]);
		novoAluno.setEndereco(atributo[9]);
		novoAluno.setComplemento(atributo[10]);
		novoAluno.setBairro(atributo[12]);
		novoAluno.setNumero(0); //TODO: não está sendo carregado no arquivo (Posição 16)
		novoAluno.setCidade(atributo[13]);
		novoAluno.setUf(atributo[14]);
		novoAluno.setPais(atributo[15]);
		
		return novoAluno;
	}
	
	/*
	 * CIDADES
	 */
	public void inserirDado(Cidade cidade) throws IOException {
		cidade.setId(pegarProximoId("cidades"));
		
		inserirDadosNoArquivo("cidades", cidade.getId() + SEPARATOR + cidade.getCidade() + SEPARATOR + cidade.getUf() + SEPARATOR + cidade.getPais());
	}

	
	/*
	 * HELPERS
	 */
 	private void inserirDadosNoArquivo(String area, String dados) {
		try {
			FileWriter arq = new FileWriter(getDestinoArquivo(area), true);
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.println(dados);
			arq.close();
		} catch (IOException e) {
			System.err.printf("Não foi possível gravar o arquivo: %s.\n", e.getMessage());
		}
		
	}
	
	private Integer pegarProximoId(String area) {
		try {
			FileReader arq = new FileReader(getDestinoArquivo(area));
			lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			Integer maiorId = 0;
			
			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);
				
				Integer currentId = Integer.parseInt(atributo[0]);
				if (currentId.compareTo(maiorId) == 1) {
					maiorId = currentId;
				}
				
				linha = lerArq.readLine();
	        }
	        
	        lerArq.close();
	        
	        return maiorId + 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//Retorna o caminho para o arquivo de dados
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

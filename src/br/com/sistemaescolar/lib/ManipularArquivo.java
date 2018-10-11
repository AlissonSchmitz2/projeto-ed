package br.com.sistemaescolar.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import br.com.sistemaescolar.model.Aluno;
import br.com.sistemaescolar.model.Cidade;
import br.com.sistemaescolar.model.Curso;
import br.com.sistemaescolar.model.Disciplina;
import br.com.sistemaescolar.model.Fase;
import br.com.sistemaescolar.model.Grade;
import br.com.sistemaescolar.model.Professor;
import br.com.sistemaescolar.model.Usuario;

public class ManipularArquivo {

	private static String USUARIOS_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\usuarios.txt";
	private static String CIDADES_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\cidades.txt";
	private static String ALUNOS_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\alunos.txt";
	private static String CURSO_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\curso.txt";
	private static String FASE_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\fase.txt";
	private static String DISCIPLINA_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\disciplina.txt";
	private static String PROFESSOR_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\professor.txt";
	private static String GRADE_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\grade.txt";

	private static String SEPARATOR = ";;;";

	private BufferedReader lerArq;

	public ManipularArquivo() {
		criarArquivosDados();
	}

	private void criarArquivosDados() {
		// Testa a existência do arquivo de dados "usuarios.txt", caso o mesmo não
		// exista, cria o diretório
		File diretorio = new File(System.getProperty("user.home") + "\\sistemaescolar\\data");
		if (!diretorio.exists()) {
			diretorio.mkdirs();
		}
	}

	/*
	 * USUÁRIOS
	 */
	public void inserirDado(Usuario usuario) {
		usuario.setId(pegarProximoId("usuarios"));

		String novosDados = criarStringDados(usuario);

		inserirDadosNoArquivo("usuarios", novosDados);
	}

	public void editarDado(Usuario usuario) {
		String novosDados = criarStringDados(usuario);

		editarDadosNoArquivo("usuarios", usuario.getId().toString(), novosDados);
	}

	public void removerDado(Usuario usuario) {
		removerDadosDoArquivo("usuarios", usuario.getId().toString());
	}

	public Usuario pegarUsuarioPorId(Integer id) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("usuarios"));
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

	public Usuario pegarUsuarioPorLogin(String login) {

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("usuarios"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] verificaLinha = linha.split(SEPARATOR);

				if (login.equals(verificaLinha[1])) {
					return new Usuario(Integer.parseInt(verificaLinha[0]), verificaLinha[1], verificaLinha[2],
							verificaLinha[3]);
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
			FileReader arq = new FileReader(pegarDestinoArquivo("usuarios"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			// Se a linha for null, significa que o arquivo de dados está vazio (Uma
			// exception é retornada)
			if (linha == null) {
				throw new Exception("Nenhum usuário cadastrado");
			}

			while (linha != null) {
				String[] verificaLinha = linha.split(SEPARATOR);

				if (login.equals(verificaLinha[1]) && senha.equals(verificaLinha[2])) {
					return new Usuario(Integer.parseInt(verificaLinha[0]), verificaLinha[1], verificaLinha[2],
							verificaLinha[3]);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			throw new Exception(e);
		}

		return null;
	}

	public List<Usuario> pegarUsuarios() {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("usuarios"));
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

	public List<Usuario> pegarUsuarios(String valorBusca) {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("usuarios"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {

				if (linha.toLowerCase().contains(valorBusca.toLowerCase())) {

					String[] atributo = linha.split(SEPARATOR);

					usuarios.add(criarUsuarioApartirAtributos(atributo));
				}

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

	private String criarStringDados(Usuario usuario) {
		return usuario.getId() + SEPARATOR + usuario.getLogin() + SEPARATOR + usuario.getSenha() + SEPARATOR
				+ usuario.getPerfil();
	}

	/*
	 * ALUNOS
	 */
	public void inserirDado(Aluno aluno) {
		aluno.setId(pegarProximoId("alunos"));

		String novosDados = criarStringDados(aluno);

		inserirDadosNoArquivo("alunos", novosDados);
	}

	public void editarDado(Aluno aluno) {
		String novosDados = criarStringDados(aluno);

		editarDadosNoArquivo("alunos", aluno.getId().toString(), novosDados);
	}

	public void removerDado(Aluno aluno) {
		removerDadosDoArquivo("alunos", aluno.getId().toString());
	}

	public Aluno pegarAlunoPorId(Integer id) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("alunos"));
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

	public Aluno pegarAlunoPorCodigo(String codigo) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("alunos"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				if (codigo.equals(atributo[1])) {
					return criarAlunoApartirAtributos(atributo);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}

	public List<Aluno> pegarAlunos() {
		List<Aluno> alunos = new ArrayList<Aluno>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("alunos"));
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

	public List<Aluno> pegarAlunos(String valorBusca) {
		List<Aluno> alunos = new ArrayList<Aluno>();
		int indicesDaBusca[] = { 0, 1, 2, 7 };

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("alunos"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {

				String[] atributo = linha.split(SEPARATOR);

				for (int i = 0; i < 4; i++) {
					if (atributo[indicesDaBusca[i]].toLowerCase().contains(valorBusca.toLowerCase())) {
						String[] atributos = linha.split(SEPARATOR);
						alunos.add(criarAlunoApartirAtributos(atributos));
						break;
					}
				}

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
		novoAluno.setSexo(atributo[3]);
		novoAluno.setDataNascimento(atributo[4]);
		novoAluno.setTelefone(atributo[5]);
		novoAluno.setCelular(atributo[6]);
		novoAluno.setCep(atributo[11]);
		novoAluno.setEmail(atributo[7]);
		novoAluno.setObservacao(atributo[8]);
		novoAluno.setEndereco(atributo[9]);
		novoAluno.setComplemento(atributo[10]);
		novoAluno.setBairro(atributo[12]);
		novoAluno.setCidade(atributo[13]);
		novoAluno.setUf(atributo[14]);
		novoAluno.setPais(atributo[15]);
		novoAluno.setNumero(atributo[16]);

		return novoAluno;
	}

	private String criarStringDados(Aluno aluno) {
		return aluno.getId() + SEPARATOR + aluno.getCodAluno() + SEPARATOR + aluno.getNomeAluno() + SEPARATOR
				+ aluno.getSexo() + SEPARATOR + aluno.getDataNascimento() + SEPARATOR + aluno.getTelefone() + SEPARATOR
				+ aluno.getCelular() + SEPARATOR + aluno.getEmail() + SEPARATOR + aluno.getObservacao() + SEPARATOR
				+ aluno.getEndereco() + SEPARATOR + aluno.getComplemento() + SEPARATOR + aluno.getCep() + SEPARATOR
				+ aluno.getBairro() + SEPARATOR + aluno.getCidade() + SEPARATOR + aluno.getUf() + SEPARATOR
				+ aluno.getPais() + SEPARATOR + aluno.getNumero();
	}

	/*
	 * CIDADES
	 */

	public void inserirDado(Cidade cidade) {
		cidade.setId(pegarProximoId("cidades"));

		String novosDados = criarStringDados(cidade);

		inserirDadosNoArquivo("cidades", novosDados);
	}

	public void editarDado(Cidade cidade) {
		String novosDados = criarStringDados(cidade);

		editarDadosNoArquivo("cidades", cidade.getId().toString(), novosDados);
	}

	public void removerDado(Cidade cidade) {
		removerDadosDoArquivo("cidades", cidade.getId().toString());
	}

	public Cidade pegarCidadePorId(Integer id) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("cidades"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				if (id.toString().equals(atributo[0])) {
					return criarCidadeApartirAtributos(atributo);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}

	public List<Cidade> pegarCidades() {
		List<Cidade> cidades = new ArrayList<Cidade>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("cidades"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				cidades.add(criarCidadeApartirAtributos(atributo));

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return cidades;
	}

	public List<Cidade> pegarCidades(String valorBusca) {
		List<Cidade> cidades = new ArrayList<Cidade>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("cidades"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {

				if (linha.toLowerCase().contains(valorBusca.toLowerCase())) {

					String[] atributo = linha.split(SEPARATOR);

					cidades.add(criarCidadeApartirAtributos(atributo));
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return cidades;
	}

	private Cidade criarCidadeApartirAtributos(String[] atributo) {
		Cidade novaCidade = new Cidade();

		novaCidade.setId(Integer.parseInt(atributo[0]));
		novaCidade.setCidade(atributo[1]);
		novaCidade.setUf(atributo[2]);
		novaCidade.setPais(atributo[3]);

		return novaCidade;
	}

	private String criarStringDados(Cidade cidade) {
		return cidade.getId() + SEPARATOR + cidade.getCidade() + SEPARATOR + cidade.getUf() + SEPARATOR
				+ cidade.getPais();
	}

	/*
	 * CURSOS
	 */

	public void inserirDado(Curso curso) {
		curso.setId(pegarProximoId("curso"));

		String novosDados = criarStringDados(curso);

		inserirDadosNoArquivo("curso", novosDados);
	}

	public void editarDado(Curso curso) {
		String novosDados = criarStringDados(curso);

		editarDadosNoArquivo("curso", curso.getId().toString(), novosDados);
	}

	public void removerDado(Curso curso) {
		removerDadosDoArquivo("curso", curso.getId().toString());
	}

	public Curso pegarCursoPorId(Integer id) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("curso"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				if (id.toString().equals(atributo[0])) {
					return criarCursoApartirAtributos(atributo);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}

	private String criarStringDados(Curso curso) {
		return curso.getId() + SEPARATOR + curso.getCurso();
	}

	private Curso criarCursoApartirAtributos(String[] atributo) {
		Curso novoCurso = new Curso();

		novoCurso.setId(Integer.parseInt(atributo[0]));
		novoCurso.setCurso(atributo[1]);

		return novoCurso;
	}

	public List<Curso> pegarCurso() {
		List<Curso> curso = new ArrayList<Curso>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("curso"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				curso.add(criarCursoApartirAtributos(atributo));

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return curso;
	}
	
	public List<Curso> pegarCurso(String valorBusca) {
		List<Curso> cursos = new ArrayList<Curso>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("curso"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {

				String[] atributo = linha.split(SEPARATOR);

				
				if (linha.toLowerCase().contains(valorBusca.toLowerCase())) {
					atributo = linha.split(SEPARATOR);
					cursos.add(criarCursoApartirAtributos(atributo));
				}

				linha = lerArq.readLine();
			}

		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return cursos;
	}


	public Curso pegarCursoPorNome(String curso) {

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("curso"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] verificaLinha = linha.split(SEPARATOR);

				if (curso.equals(verificaLinha[1])) {
					return new Curso(Integer.parseInt(verificaLinha[0]), verificaLinha[1]);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}

	/*
	 * FASES
	 */

	public void inserirDado(Fase fase) {
		fase.setId(pegarProximoId("fase"));

		String novosDados = criarStringDados(fase);

		inserirDadosNoArquivo("fase", novosDados);
	}

	public void editarDado(Fase fase) {
		String novosDados = criarStringDados(fase);

		editarDadosNoArquivo("fase", fase.getId().toString(), novosDados);
	}

	public void removerDado(Fase fase) {
		removerDadosDoArquivo("fase", fase.getId().toString());
	}

	public Fase pegarFasePorId(Integer id) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("fase"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				if (id.toString().equals(atributo[0])) {
					return criarFaseApartirAtributos(atributo);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}

	public Fase pegarFasePorFaseCurso(String fase, Integer curso) {

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("fase"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] verificaLinha = linha.split(SEPARATOR);

				if (fase.equals(verificaLinha[1]) && curso.equals(Integer.parseInt(verificaLinha[2]))) {
					return new Fase(Integer.parseInt(verificaLinha[0]), verificaLinha[1],
							Integer.parseInt(verificaLinha[2]));
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}

	private String criarStringDados(Fase fase) {
		return fase.getId() + SEPARATOR + fase.getFase() + SEPARATOR + fase.getIdCurso();
	}

	private Fase criarFaseApartirAtributos(String[] atributo) {
		Fase novaFase = new Fase();

		novaFase.setId(Integer.parseInt(atributo[0]));
		novaFase.setFase(atributo[1]);
		novaFase.setIdCurso(Integer.parseInt(atributo[2]));

		return novaFase;
	}

	public List<Fase> pegarFase() {
		List<Fase> fase = new ArrayList<Fase>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("fase"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				fase.add(criarFaseApartirAtributos(atributo));

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return fase;
	}

	/*
	 * DISCIPLINA
	 */

	public void inserirDado(Disciplina disciplina) {
		disciplina.setId(pegarProximoId("disciplina"));

		String novosDados = criarStringDados(disciplina);

		inserirDadosNoArquivo("disciplina", novosDados);
	}

	public void editarDado(Disciplina disciplina) {
		String novosDados = criarStringDados(disciplina);

		editarDadosNoArquivo("disciplina", disciplina.getId().toString(), novosDados);
	}

	public void removerDado(Disciplina disciplina) {
		removerDadosDoArquivo("disciplina", disciplina.getId().toString());
	}

	public Disciplina pegarDisciplinaPorId(Integer id) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("disciplina"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				if (id.toString().equals(atributo[0])) {
					return criarDisciplinaApartirAtributos(atributo);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}

	private String criarStringDados(Disciplina disciplina) {
		return disciplina.getId() + SEPARATOR + disciplina.getCodDisciplina() + 
				SEPARATOR + disciplina.getDisciplina();
	}

	private Disciplina criarDisciplinaApartirAtributos(String[] atributo) {
		Disciplina novaDisciplina = new Disciplina();

		novaDisciplina.setId(Integer.parseInt(atributo[0]));
		novaDisciplina.setCodDisciplina(Integer.parseInt(atributo[1]));
		novaDisciplina.setDisciplina(atributo[2]);

		return novaDisciplina;
	}

	public Disciplina pegarDisciplinaPorNome(String disciplina) {

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("disciplina"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] verificaLinha = linha.split(SEPARATOR);

				if (disciplina.equals(verificaLinha[2])) {
					return new Disciplina(Integer.parseInt(verificaLinha[0]), Integer.parseInt(verificaLinha[1]),
							verificaLinha[2]);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}

	public List<Disciplina> pegarDisciplina() {
		List<Disciplina> disciplina = new ArrayList<Disciplina>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("disciplina"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				disciplina.add(criarDisciplinaApartirAtributos(atributo));

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return disciplina;
	}

	/*
	 * PROFESSOR
	 */

	public void inserirDado(Professor prof) {
		prof.setId(pegarProximoId("professor"));

		String novosDados = criarStringDados(prof);

		inserirDadosNoArquivo("professor", novosDados);
	}

	public void editarDado(Professor prof) {
		String novosDados = criarStringDados(prof);

		editarDadosNoArquivo("professor", prof.getId().toString(), novosDados);
	}

	public void removerDado(Professor prof) {
		removerDadosDoArquivo("professor", prof.getId().toString());
	}

	public Professor pegarProfessorPorId(Integer id) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("professor"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				if (id.toString().equals(atributo[0])) {
					return criarProfessorApartirAtributos(atributo);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}

	private String criarStringDados(Professor prof) {
		return prof.getId() + SEPARATOR + prof.getProfessor() + SEPARATOR + prof.getTituloDocente();
	}

	private Professor criarProfessorApartirAtributos(String[] atributo) {
		Professor novoProf = new Professor();

		novoProf.setId(Integer.parseInt(atributo[0]));
		novoProf.setProfessor(atributo[1]);
		novoProf.setTituloDocente(atributo[2]);

		return novoProf;
	}

	public List<Professor> pegarProfessor() {
		List<Professor> professor = new ArrayList<Professor>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("professor"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				professor.add(criarProfessorApartirAtributos(atributo));

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return professor;
	}
	
	public List<Professor> pegarProfessor(String valorBusca) {
		List<Professor> professor = new ArrayList<Professor>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("professor"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				
				if (linha.toLowerCase().contains(valorBusca.toLowerCase())) {

					String[] atributo = linha.split(SEPARATOR);

					professor.add(criarProfessorApartirAtributos(atributo));
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return professor;
	}

	public Professor pegarProfessorPorNome(String prof) {

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("professor"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] verificaLinha = linha.split(SEPARATOR);

				if (prof.equals(verificaLinha[1])) {
					return new Professor(Integer.parseInt(verificaLinha[0]), verificaLinha[1], verificaLinha[2]);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}
	
	/*
	 * GRADE
	 */
	
	public void inserirDado(Grade grade) {
		grade.setId(pegarProximoId("grade"));

		String novosDados = criarStringDados(grade);

		inserirDadosNoArquivo("grade", novosDados);
	}

	public void editarDado(Grade grade) {
		String novosDados = criarStringDados(grade);

		editarDadosNoArquivo("grade", grade.getId().toString(), novosDados);
	}

	public void removerDado(Grade grade) {
		removerDadosDoArquivo("grade", grade.getId().toString());
	}

	public Grade pegarGradePorId(Integer id) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("grade"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				if (id.toString().equals(atributo[0])) {
					return criarGradeApartirAtributos(atributo);
				}

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return null;
	}

	private String criarStringDados(Grade grade) {
		return grade.getId() + SEPARATOR + grade.getId_fase() + SEPARATOR + grade.getId_disciplina() + SEPARATOR +
				grade.getId_professor();
	}

	private Grade criarGradeApartirAtributos(String[] atributo) {
		Grade novaGrade = new Grade();

		novaGrade.setId(Integer.parseInt(atributo[0]));
		novaGrade.setId_fase(Integer.parseInt(atributo[1]));
		novaGrade.setId_disciplina(Integer.parseInt(atributo[2]));
		novaGrade.setId_professor(Integer.parseInt(atributo[3]));

		return novaGrade;
	}

	public List<Grade> pegarGrade() {
		List<Grade> grade = new ArrayList<Grade>();

		try {
			FileReader arq = new FileReader(pegarDestinoArquivo("grade"));
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				grade.add(criarGradeApartirAtributos(atributo));

				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return grade;
	}

	/*
	 * HELPERS
	 */
	// Método auxiliar responsável por gravar dos dados no arquivo
	private void inserirDadosNoArquivo(String area, String dados) {
		try {
			FileWriter arq = new FileWriter(pegarDestinoArquivo(area), true);
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.println(dados);
			arq.close();
		} catch (IOException e) {
			System.err.printf("Não foi possível gravar o arquivo: %s.\n", e.getMessage());
		}

	}

	// Método auxiliar responsável por atualizar dados no arquivo
	public void editarDadosNoArquivo(String area, String id, String dados) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo(area));
			lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			StringBuffer inputBuffer = new StringBuffer();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				// Se encontrar a linha com o ID correspondente, substitui com os novos dados
				if (id.equals(atributo[0])) {
					linha = dados;
				}

				inputBuffer.append(linha);
				inputBuffer.append('\n');

				linha = lerArq.readLine();
			}

			String inputStr = inputBuffer.toString();

			lerArq.close();

			FileOutputStream fileOut = new FileOutputStream(pegarDestinoArquivo(area));
			fileOut.write(inputStr.getBytes());
			fileOut.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

	// Método auxiliar responsável por remover uma linha de dados do arquivo
	private void removerDadosDoArquivo(String area, String id) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo(area));
			lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			StringBuffer inputBuffer = new StringBuffer();

			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);

				// Se a linha não for a linha ser removida, insere no buffer para ser salvo
				// posteriormente
				if (!id.equals(atributo[0])) {
					inputBuffer.append(linha);
					inputBuffer.append('\n');
				}

				linha = lerArq.readLine();
			}

			String inputStr = inputBuffer.toString();

			lerArq.close();

			FileOutputStream fileOut = new FileOutputStream(pegarDestinoArquivo(area));
			fileOut.write(inputStr.getBytes());
			fileOut.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

	// Retorna o próximo ID disponível

	// Pega o próximo ID disponível no arquivo
	private Integer pegarProximoId(String area) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo(area));
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
			return 1;
		}
	}

	// Retorna o caminho para o arquivo de dados
	private String pegarDestinoArquivo(String area) {
		switch (area) {
		case "usuarios":
			return new File(USUARIOS_PATH).getAbsolutePath();
		case "cidades":
			return new File(CIDADES_PATH).getAbsolutePath();
		case "alunos":
			return new File(ALUNOS_PATH).getAbsolutePath();
		case "curso":
			return new File(CURSO_PATH).getAbsolutePath();
		case "fase":
			return new File(FASE_PATH).getAbsolutePath();
		case "disciplina":
			return new File(DISCIPLINA_PATH).getAbsolutePath();
		case "professor":
			return new File(PROFESSOR_PATH).getAbsolutePath();
		case "grade":
			return new File(GRADE_PATH).getAbsolutePath();
		}
		return null;
	}
}

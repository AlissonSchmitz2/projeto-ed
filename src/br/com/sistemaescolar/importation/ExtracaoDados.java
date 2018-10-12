package br.com.sistemaescolar.importation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Configuracoes;

public class ExtracaoDados extends ManipuladorRegistro {
	private String caminhoArquivo;
	private Configuracoes configuracoes;
	
	private HeaderArquivo header;
	private TrailerArquivo trailer;
	private Map<String, ResumoFase> resumos;
	
	private ManipularArquivo aM = new ManipularArquivo();
	
	private Map<String, String> disciplinasCadastradas = new HashMap<String, String>();
	
	public ExtracaoDados(String caminhoArquivo, Configuracoes configuracoes) throws Exception {
		this.caminhoArquivo = caminhoArquivo;
		this.configuracoes = configuracoes;
		
		carregarDisciplinasCadastradas();
		processarArquivoDados();
		validarArquivo();
	}
	
	public HeaderArquivo getHeader() {
		return header;
	}
	
	public TrailerArquivo getTrailer() {
		return trailer;
	}
	
	public Map<String, ResumoFase> getResumos() {
		return resumos;
	}
	
	/**
	 * Retorna todas as disciplinas de todas as fases
	 */
	public Map<String, ResumoDisciplina> getTodasDisciplinas() {
		Map<String, ResumoDisciplina> disciplinas = new HashMap<String, ResumoDisciplina>();
		
		for (Entry<String, ResumoFase> entradaFase : getResumos().entrySet()){
	        for (ResumoDisciplina resumoDisciplina : entradaFase.getValue().getDisciplinas().values()){
	        	
	        	disciplinas.put(resumoDisciplina.getCodigoDisciplina(), resumoDisciplina);
	        }
	    }
		
		return disciplinas;
	}
	
	/**
	 * Retorna todas os professores de todas as disciplinas
	 */
	public Map<String, ResumoProfessor> getTodosProfessores() {
		Map<String, ResumoProfessor> professores = new HashMap<String, ResumoProfessor>();
		
		for (Entry<String, ResumoFase> entradaFase : getResumos().entrySet()){
	        for (Entry<String, ResumoDisciplina> entradaDisciplina : entradaFase.getValue().getDisciplinas().entrySet()){
	        	entradaDisciplina.getValue().getProfessores().values().stream()
	        		.forEach(resumoProfessor -> professores.put(resumoProfessor.getNome(), resumoProfessor));
	        }
	    }
		
		return professores;
	}
	
	/**
	 * Disciplinas cadastradas (Baseado no código da disciplina a ser importada, é possível recuperar o nome)
	 */
	public void carregarDisciplinasCadastradas() {
		aM.pegarDisciplinas().stream()
			.forEach(disciplina -> disciplinasCadastradas.put(String.format("%06d", disciplina.getCodDisciplina()), disciplina.getDisciplina()));
	}
	
	/**
	 * Processa o arquivo, extraindo cada um dos seus registros
	 */
	private void processarArquivoDados() throws Exception {
		//Extrai dados do Header
		header = extrairHeaderArquivo();
		
		trailer = extrairTrailerArquivo();
		
		resumos = extrairResumosFases();
	}
	
	/**
	 * Valida o arquivo para validar os registros extraídos
	 */
	private void validarArquivo() throws Exception {
		//Valida Header
		header.validar(configuracoes);
		
		//Valida Trailer
		trailer.validar(configuracoes);
		
		//Itera sobre cada um dos resumo fase
		Integer totalRegistros = resumos.size();
		for (ResumoFase resumoFase : resumos.values()){
			//Valida cada um dos resumos de fase
			resumoFase.validar(configuracoes);
			
			totalRegistros += resumoFase.getQtdeDisciplinasEsperadas() + resumoFase.getQtdeProfessoresEsperados();
		}
		
		//É esperado que a quantidade de registros informada no TRAILER	seja a mesma quantidade extraída
		if (trailer.getTotalRegistros().compareTo(totalRegistros) != 0) {
			dispararErro("A quantidade de registros no arquivo \"" + totalRegistros + "\" é "
					+ "diferente da informada no TRAILER \"" + trailer.getTotalRegistros() +"\"");
		}
		
		//Neste método ficam as validações globais do arquivo (Aquelas que não são feitas nos registros individualmente)
	}
	
	/**
	 * Extrai do arquivo os resumos Fases, Disciplinas e Professores (Com códigos 1, 2 e 3)
	 */
	private Map<String, ResumoFase> extrairResumosFases() throws Exception {
		int iLinha = 0; //Varíavel para saber a linha ao exibir log de erro
		
		try {
			BufferedReader bufferArquivo = new BufferedReader(new FileReader(caminhoArquivo));
	
			String linha = bufferArquivo.readLine();
			
			Map<String, ResumoFase> resumosExtraidos = new HashMap<String, ResumoFase>();
			
			//Variáveis para controle ordem de inserção dados
			ResumoFase ultimoResumoFase = null;
			ResumoDisciplina ultimoResumoDisciplina = null;
			
			while (linha != null) {
				iLinha++;
				
				if (!linha.isEmpty()) {
					Integer tipoRegistro = Integer.parseInt(linha.substring(0, 1));
					
					//FASE
					if (tipoRegistro.equals(ResumoFase.CODIGO_REGISTRO)) {
						//Cria objeto apartir da linha					
						ultimoResumoFase = criarObjetoResumoFase(linha);
						
						//Adiciona fase ao resumos
						resumosExtraidos.put(ultimoResumoFase.getFase(), ultimoResumoFase);
						
						//Reseta controle última disciplina
						ultimoResumoDisciplina = null;
						
					//DISCIPLINA
					} else if (tipoRegistro.equals(ResumoDisciplina.CODIGO_REGISTRO)) {
						if (ultimoResumoFase == null) {
							bufferArquivo.close();
							throw new Exception("Não é possível inserir disciplina sem uma fase.");
						}
						
						//Cria objeto apartir da linha
						ultimoResumoDisciplina = criarObjetoResumoDisciplina(linha);
						
						//Adiciona disciplina a fase
						ultimoResumoFase.inserirDisciplina(ultimoResumoDisciplina);
						
					//PROFESSOR
					} else if (tipoRegistro.equals(ResumoProfessor.CODIGO_REGISTRO)) {
						if (ultimoResumoDisciplina == null) {
							bufferArquivo.close();
							throw new Exception("Não é possível inserir professor sem uma disciplina.");
						}
						
						ultimoResumoDisciplina.inserirProfessor(criarObjetoResumoProfessor(linha));
					} else if (tipoRegistro.equals(HeaderArquivo.CODIGO_REGISTRO) || tipoRegistro.equals(TrailerArquivo.CODIGO_REGISTRO)) {
						//Somente para validação
					} else {
						bufferArquivo.close();
						throw new Exception("Foi encontrado um tipo de registro inválido.");
					}
				}
				
				linha = bufferArquivo.readLine();
			}
			
			bufferArquivo.close();
			
			return resumosExtraidos;
		} catch(Exception error) {
			dispararErro("RESUMO é inválido. " + error.getMessage() + " - Linha " + iLinha);
		}
		
		return null;
	}
	
	/**
	 * Processa o arquivo, extraindo o header (Código 0)
	 */
	private HeaderArquivo extrairHeaderArquivo() throws Exception {
		try {
			BufferedReader bufferArquivo = new BufferedReader(new FileReader(caminhoArquivo));
	
			String linha = bufferArquivo.readLine();
			String linhaDadosEncontrada = null;
			
			while (linha != null) {
				if (!linha.isEmpty()) {
					Integer tipoRegistro = Integer.parseInt(linha.substring(0, 1));
					
					if (tipoRegistro.equals(HeaderArquivo.CODIGO_REGISTRO)) {
						if (linhaDadosEncontrada != null) {
							dispararErro("O arquivo possui dois ou mais HEADERs");
						}
						
						linhaDadosEncontrada = linha;
					}
				}
				
				linha = bufferArquivo.readLine();
			}
			
			bufferArquivo.close();
			
			if (linhaDadosEncontrada != null) {
				return criarObjetoHeaderArquivo(linhaDadosEncontrada);
			}
		} catch(Exception error) {
			dispararErro("HEADER é inválido: " + error.getMessage());
		}
		
		dispararErro("O arquivo não possui um HEADER");
		
		return null;
	}
	
	/**
	 * Processa o arquivo, extraindo o trailer do arquivo (Código 9)
	 */
	private TrailerArquivo extrairTrailerArquivo() throws Exception {
		try {
			BufferedReader bufferArquivo = new BufferedReader(new FileReader(caminhoArquivo));

			String linha = bufferArquivo.readLine();
			String linhaDadosEncontrada = null;
			
			while (linha != null) {
				if (!linha.isEmpty()) {
					Integer tipoRegistro = Integer.parseInt(linha.substring(0, 1));
					
					if (tipoRegistro.equals(TrailerArquivo.CODIGO_REGISTRO)) {
						if (linhaDadosEncontrada != null) {
							dispararErro("O arquivo possui dois ou mais TRAILERs");
						}
						
						linhaDadosEncontrada = linha;
					}
				}
				
				linha = bufferArquivo.readLine();
			}
			
			bufferArquivo.close();
			
			if (linhaDadosEncontrada != null) {
				return criarObjetoTrailerArquivo(linhaDadosEncontrada);
			}
		} catch(Exception error) {
			dispararErro("TRAILER é inválido: " + error.getMessage());
		}
		
		dispararErro("O arquivo não possui um TRAILER");
		
		return null;
	}
	
	/**
	 * Constrói o objeto ResumoFase apartir da linha
	 */
	private ResumoFase criarObjetoResumoFase(String linha) throws Exception {
		Integer tipoRegistro = Integer.parseInt(linha.substring(0, 1));
		String fase = linha.substring(1, 8);
		Integer qtdeDisciplinas = Integer.parseInt(linha.substring(8, 10));
		Integer qtdeProfessores = Integer.parseInt(linha.substring(10, 12));
		
		if (!tipoRegistro.equals(ResumoFase.CODIGO_REGISTRO)) {
			dispararErro("A linha informada não pertence a um Resumo de Fase");
		}

		return new ResumoFase(fase, qtdeDisciplinas, qtdeProfessores);
	}
	
	/**
	 * Constrói o objeto ResumoDisciplina apartir da linha
	 */
	private ResumoDisciplina criarObjetoResumoDisciplina(String linha) throws Exception {
		Integer tipoRegistro = Integer.parseInt(linha.substring(0, 1));
		String codigoDisciplina = linha.substring(1, 7);
		String codigoDiaSemana = linha.substring(7, 9);
		Integer qtdeProfessores = Integer.parseInt(linha.substring(9, 11));
		
		if (!tipoRegistro.equals(ResumoDisciplina.CODIGO_REGISTRO)) {
			dispararErro("A linha informada não pertence a um Resumo de Disciplina");
		}

		return new ResumoDisciplina(codigoDisciplina, disciplinasCadastradas.get(codigoDisciplina), codigoDiaSemana, qtdeProfessores);
	}
	
	/**
	 * Constrói o objeto ResumoProfessor apartir da linha
	 */
	private ResumoProfessor criarObjetoResumoProfessor(String linha) throws Exception {
		Integer tipoRegistro = Integer.parseInt(linha.substring(0, 1));
		String nome = linha.substring(1, 41);
		String codigoTitulo = linha.substring(41, 43);
		
		if (!tipoRegistro.equals(ResumoProfessor.CODIGO_REGISTRO)) {
			dispararErro("A linha informada não pertence a um Resumo de Professor");
		}

		return new ResumoProfessor(nome, codigoTitulo);
	}
	
	/**
	 * Constrói o objeto HeaderArquivo apartir da linha
	 */
	private HeaderArquivo criarObjetoHeaderArquivo(String linha) throws Exception {
		Integer tipoRegistro = Integer.parseInt(linha.substring(0, 1));
		String nomeCurso = linha.substring(1, 11);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dataProcessamento = sdf.parse(linha.substring(11, 19));
		String faseInicial = linha.substring(19, 26);
		String faseFinal = linha.substring(26, 33);
		Integer numeroSequencial = Integer.parseInt(linha.substring(33, 40));
		String versaoLayout = linha.substring(40, 43);
		
		if (!tipoRegistro.equals(HeaderArquivo.CODIGO_REGISTRO)) {
			dispararErro("A linha informada não pertence ao Header do Arquivo");
		}
		
		return new HeaderArquivo(nomeCurso, dataProcessamento, faseInicial, faseFinal, numeroSequencial, versaoLayout);
	}
	
	/**
	 * Constrói o objeto TrailerArquivo apartir da linha
	 */
	private TrailerArquivo criarObjetoTrailerArquivo(String linha) throws Exception {
		Integer tipoRegistro = Integer.parseInt(linha.substring(0, 1));
		Integer totalRegistros = Integer.parseInt(linha.substring(1, 11));
		
		if (!tipoRegistro.equals(TrailerArquivo.CODIGO_REGISTRO)) {
			dispararErro("A linha informada não pertence ao Trailer do Arquivo");
		}
		
		return new TrailerArquivo(totalRegistros);
	}
}

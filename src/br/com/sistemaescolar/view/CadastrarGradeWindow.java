package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Curso;
import br.com.sistemaescolar.model.Disciplina;
import br.com.sistemaescolar.model.Fase;
import br.com.sistemaescolar.model.Grade;
import br.com.sistemaescolar.model.GradeItem;
import br.com.sistemaescolar.model.Professor;
import br.com.sistemaescolar.observer.ObserverGrade;
import br.com.sistemaescolar.observer.SubjectGrade;

public class CadastrarGradeWindow extends AbstractWindowFrame implements SubjectGrade {

	private static final long serialVersionUID = 10914486141164967L;
	
	ManipularArquivo aM = new ManipularArquivo();
	private Grade grade = new Grade();
	DefaultTableModel model = new DefaultTableModel();
	private JLabel labes;
	private JComboBox<String> cbxCurso, cbxFases;
	
	private List<Curso> listaCursos;
	private List<Fase> listaFases;
	private List<Disciplina> listaDisciplinas;
	private List<Professor> listaProfessores;
	private ArrayList<ObserverGrade> observers = new ArrayList<ObserverGrade>();
	private JComboBox<String> cbxDisciplina;
	private JComboBox<String> cbxProfessor;
	private JButton btnAdd, btnSalvar1;
	private JLabel label;

	private JPanel painel;
	private JScrollPane scrollpane;
	private JTable tabela;
	int indiceLinhaGrid = 0;

	public CadastrarGradeWindow() {
		super("Cadastrar Grade");
		this.grade = new Grade();
		inicializar();
	}
	
	public CadastrarGradeWindow(Grade grade) {
		super("Editar Grade");
		this.grade = grade;
		inicializar();

		setarValores(grade);
	}
	
	private void inicializar() {
		listaCursos = aM.pegarCursos();
		listaFases = aM.pegarFases();
		listaDisciplinas = aM.pegarDisciplinas();
		listaProfessores = aM.pegarProfessores();
		
		criarComponentes();
		criarGrid(null);
	}

	public void criarComponentes() {

		labes = new JLabel("Fase:");
		labes.setBounds(410, 60, 250, 25);
		getContentPane().add(labes);

		cbxFases = new JComboBox<String>();
		cbxFases.addItem("-Selecione-");
		cbxFases.setBounds(480, 60, 170, 25);
		cbxFases.setToolTipText("Informe a fase");
		getContentPane().add(cbxFases);

		labes = new JLabel("Curso:");
		labes.setBounds(410, 20, 250, 25);
		getContentPane().add(labes);

		cbxCurso = new JComboBox<String>();
		cbxCurso.addItem("-Selecione-");
		opcoesCursos(listaCursos).forEach(cursos -> cbxCurso.addItem(cursos));
		cbxCurso.setBounds(480, 20, 170, 25);
		cbxCurso.setToolTipText("Informe o curso");
		getContentPane().add(cbxCurso);

		cbxCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cursoSelecionado = (String) cbxCurso.getSelectedItem();

				// Reseta estados e cidades
				cbxFases.removeAllItems();
				cbxFases.addItem("-Selecione-");

				if (cursoSelecionado == null) {
					return;
				}

				opcoesFases(listaFases, cursoSelecionado).forEach(fase -> cbxFases.addItem(fase));
				// TODO: Atualizar ComboBox ao voltar na opção --Selecione-- do Curso
				// TODO:Adicionar disciplinas ao JTable, se já cadastradas
			}
		});

		label = new JLabel("Disciplina:");
		label.setBounds(410, 130, 130, 45);
		getContentPane().add(label);

		cbxDisciplina = new JComboBox<String>();
		cbxDisciplina.setBounds(480, 140, 170, 25);
		getContentPane().add(cbxDisciplina);
		cbxDisciplina.addItem("-Selecione-");
		opcoesDisciplinas(listaDisciplinas).forEach(disciplinas -> cbxDisciplina.addItem(disciplinas));

		label = new JLabel("Professor:");
		label.setBounds(660, 130, 70, 45);
		getContentPane().add(label);

		cbxProfessor = new JComboBox<String>();
		cbxProfessor.setBounds(730, 140, 170, 25);
		getContentPane().add(cbxProfessor);
		cbxProfessor.addItem("-Selecione-");
		opcoesProfessores(listaProfessores).forEach(professores -> cbxProfessor.addItem(professores));

		btnAdd = new JButton("+");
		btnAdd.setBounds(959, 140, 50, 25);
		getContentPane().add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validarCamposObrigatorios()) {
					JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para cadastrar!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {
					addListaJTable(cbxDisciplina.getSelectedItem().toString(),
							cbxProfessor.getSelectedItem().toString());
					limparFormulario();
				}
			}
		});

		btnSalvar1 = new JButton("Salvar");
		btnSalvar1.setBounds(900, 320, 110, 25);
		getContentPane().add(btnSalvar1);
		btnSalvar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (indiceLinhaGrid == 0) {
					JOptionPane.showMessageDialog(rootPane, "Informe as disciplinas para cadastro!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {
					salvarGrade();
				}
			}
		});
	}

	public boolean validarCamposObrigatorios() {
		if ("-Selecione-".equals(cbxDisciplina.getSelectedItem()) ||
			"-Selecione-".equals(cbxProfessor.getSelectedItem())  ||
			"-Selecione-".equals(cbxCurso.getSelectedItem())   	  ||
			"-Selecione-".equals(cbxFases.getSelectedItem())) {

			return true;
		}
		return false;
	}

	private void salvarGrade() {
		Curso curso = aM.pegarCursoPorNome(cbxCurso.getSelectedItem().toString());
		Fase fase = aM.pegarFasePorNomeFaseIdCurso(cbxFases.getSelectedItem().toString(), curso.getId());
		
		//Antes de continuar, verifica se já não existe um grade para o curso e fase
		Grade gradeExistente = aM.pegarGradePorIdFase(fase.getId(), false);
		if (
				(grade.getId() == null && gradeExistente != null) || 
				(grade.getId() != null && gradeExistente != null && gradeExistente.getId().compareTo(grade.getId()) != 0)
		) {
			JOptionPane.showMessageDialog(rootPane, "Já existe uma grade para o curso e fase selecionados!", "",
					JOptionPane.ERROR_MESSAGE, null);
			
			return;
		}
		
		grade.setFase(fase);
		grade.clearItens();
		
		//Percorre os items da grid
		for (int i = 0; i < indiceLinhaGrid; i++) {
			String valueRowDisciplina = (String) tabela.getValueAt(i, 1);
			Disciplina disciplina = aM.pegarDisciplinaPorNome(valueRowDisciplina);
			
			String valueRowProf = (String) tabela.getValueAt(i, 2);
			Professor professor = aM.pegarProfessorPorNome(valueRowProf);
			
			GradeItem novoItemGrade = new GradeItem();
			novoItemGrade.setDisciplina(disciplina);
			novoItemGrade.setProfessor(professor);
			novoItemGrade.setCodigoDiaSemana("01"); //TODO: pegar da grid
			
			//Insere os items no objeto grade
			grade.setItem(novoItemGrade);
		}
		
		//Edição
		if (grade.getId() != null) {
			aM.editarDado(grade);
			
			notifyObservers(grade);
			JOptionPane.showMessageDialog(null, "Grade salva com sucesso!");
			
			limparFormulario();
			setVisible(false);
		}

		//Cadastro
		if (grade.getId() == null) {
			aM.inserirDado(grade);
			
			limparFormulario();
			JOptionPane.showMessageDialog(null, "Grade salva com sucesso!");
		}
	}

	// Obtem lista de Cursos
	private List<String> opcoesCursos(List<Curso> cursos) {
		return cursos.stream().map(curso -> curso.getNome()).distinct().collect(Collectors.toList());
	}

	// Obtem lista de Fases referente ao curso
	private List<String> opcoesFases(List<Fase> fases, String curso) {

		Curso _curso = aM.pegarCursoPorNome(curso);
		int idCurso = _curso.getId();

		return fases.stream().filter(fase -> idCurso == fase.getCurso().getId()).map(fase -> fase.getNome()).distinct()
				.collect(Collectors.toList());
	}

	// Obtem lista de Disciplinas
	private List<String> opcoesDisciplinas(List<Disciplina> disciplinas) {
		return disciplinas.stream().map(disciplina -> disciplina.getNome()).distinct()
				.collect(Collectors.toList());
	}

	// Obtem lista de Professores
	private List<String> opcoesProfessores(List<Professor> professores) {
		return professores.stream().map(professor -> professor.getNome()).distinct().collect(Collectors.toList());
	}

	// Manipulação da JTable

	// TODO:Organizar manipulação com Table a uma classe DisciplinasTableModel

	private void addListaJTable(String nomeDisciplina, String nomeProfessor) {
		Disciplina disciplina = aM.pegarDisciplinaPorNome(nomeDisciplina);
		Professor professor = aM.pegarProfessorPorNome(nomeProfessor);

		String codDisciplina = String.valueOf(disciplina.getCodDisciplina());
		String[] linha = { codDisciplina, disciplina.getNome(), professor.getNome() };
		criarGrid(linha);
		
		indiceLinhaGrid++;
	}
	
	private void addListaJTable(Disciplina disciplina, Professor professor) {
		String codDisciplina = String.valueOf(disciplina.getCodDisciplina());
		String[] linha = { codDisciplina, disciplina.getNome(), professor.getNome() };
		criarGrid(linha);
		
		indiceLinhaGrid++;
	}

	public void criarGrid(String[] linha) {
		painel = new JPanel();
		getContentPane().add(painel);
		// Colunas da Grid

		String[] colunas = { "Codigo da disciplina", "Disciplina", "Professor" };

		model.setColumnIdentifiers(colunas);
		model.insertRow(indiceLinhaGrid, linha);
		model.setNumRows(7);

		tabela = new JTable(model);
		scrollpane = new JScrollPane(tabela);
		scrollpane.setBounds(410, 170, 600, 135);
		setLayout(null);
		scrollpane.setVisible(true);
		add(scrollpane);
	}

	public void limparJTable() {
		String[] linha = {};

		for (int i = 0; i < 7; i++) {
			model.removeRow(i);
			model.insertRow(i, linha);
		}

		indiceLinhaGrid = 0;
	}

	public void limparFormulario() {
		cbxDisciplina.setSelectedIndex(0);
		cbxProfessor.setSelectedIndex(0);
		
		limparJTable();
	}
	
	private void setarValores(Grade grade) {
		cbxCurso.setSelectedItem(grade.getFase().getCurso().getNome());

		cbxFases.setSelectedItem(grade.getFase().getNome());
		
		//Preenche o grid com os items
		grade.getItens().forEach(gradeItem -> addListaJTable(gradeItem.getDisciplina(), gradeItem.getProfessor()));
	}

	@Override
	public void addObserver(ObserverGrade o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(ObserverGrade o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Grade grade) {
		Iterator<ObserverGrade> it = observers.iterator();
		while (it.hasNext()) {
			ObserverGrade observer = (ObserverGrade) it.next();
			observer.update(grade);
		}
	}
}

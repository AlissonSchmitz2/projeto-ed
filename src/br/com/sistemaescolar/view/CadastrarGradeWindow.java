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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Curso;
import br.com.sistemaescolar.model.Disciplina;
import br.com.sistemaescolar.model.Fase;
import br.com.sistemaescolar.model.Grade;
import br.com.sistemaescolar.model.Professor;
import br.com.sistemaescolar.observer.ObserverGrade;
import br.com.sistemaescolar.observer.SubjectGrade;
import br.com.sistemaescolar.table.model.GradeTableModel;

public class CadastrarGradeWindow extends AbstractWindowFrame implements SubjectGrade {

	private static final long serialVersionUID = 10914486141164967L;
	
	ManipularArquivo aM = new ManipularArquivo();
	private Grade grade = new Grade();
	private GradeTableModel modelGradesEdicao;
	private List<Grade> listaGrades = new ArrayList<Grade>();
	DefaultTableModel model = new DefaultTableModel();
	private JLabel labes;
	private JComboBox<String> cbxCurso, cbxFases;
	JTable tabela;
	List<Curso> curso;
	List<Fase> fase;
	List<Disciplina> disciplina;
	List<Professor> professor;
	int numRow = 0;
	private ArrayList<ObserverGrade> observers = new ArrayList<ObserverGrade>();
	
	private JTextField txfCod;
	private JComboBox<String> cbxDisciplina;
	private JComboBox<String> cbxProfessor;
	private JButton btnAdd, btnSalvar1;
	private JLabel label;

	private JPanel painel;
	private JScrollPane scrollpane;

	Disciplina disc = new Disciplina();
	Professor prof = new Professor();

	public CadastrarGradeWindow() {
		super("Grade");
		curso = aM.pegarCursos();
		fase = aM.pegarFases();
		disciplina = aM.pegarDisciplinas();
		professor = aM.pegarProfessores();
		criarComponentes();
		criarGrid(null);
	}
	
	public CadastrarGradeWindow(Grade grade, GradeTableModel modelGradesEdicao) {
		super("Editar Grade");
		curso = aM.pegarCursos();
		fase = aM.pegarFases();
		disciplina = aM.pegarDisciplinas();
		professor = aM.pegarProfessores();
		criarComponentes();
		criarGrid(null);
		setarValores(grade);
		this.modelGradesEdicao = modelGradesEdicao;
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
		opcoesCursos(curso).forEach(cursos -> cbxCurso.addItem(cursos));
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

				// Adiciona op��es estados
				opcoesFases(fase, cursoSelecionado).forEach(fase -> cbxFases.addItem(fase));
				// TODO: Atualizar ComboBox ao voltar na op��o --Selecione-- do Curso
				// TODO:Adicionar disciplinas ao JTable, se j� cadastradas
			}
		});

		label = new JLabel("Disciplina:");
		label.setBounds(410, 130, 130, 45);
		getContentPane().add(label);

		cbxDisciplina = new JComboBox<String>();
		cbxDisciplina.setBounds(480, 140, 170, 25);
		getContentPane().add(cbxDisciplina);
		cbxDisciplina.addItem("-Selecione-");
		opcoesDisciplinas(disciplina).forEach(disciplinas -> cbxDisciplina.addItem(disciplinas));

		label = new JLabel("Professor:");
		label.setBounds(660, 130, 70, 45);
		getContentPane().add(label);

		cbxProfessor = new JComboBox<String>();
		cbxProfessor.setBounds(730, 140, 170, 25);
		getContentPane().add(cbxProfessor);
		cbxProfessor.addItem("-Selecione-");
		opcoesProfessores(professor).forEach(professores -> cbxProfessor.addItem(professores));

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
					numRow++;
				}
			}
		});

		btnSalvar1 = new JButton("Salvar");
		btnSalvar1.setBounds(900, 320, 110, 25);
		getContentPane().add(btnSalvar1);
		btnSalvar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numRow == 0) {
					JOptionPane.showMessageDialog(rootPane, "Informe as disciplinas para cadastro!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {
					cadastrarGrade();
					limparJTable();
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

	public void cadastrarGrade() {

		Grade grade = new Grade();		

		for (int i = 0; i < numRow; i++) {
			Curso curso = aM.pegarCursoPorNome(cbxCurso.getSelectedItem().toString());
			Fase fase = aM.pegarFasePorFaseCurso(cbxFases.getSelectedItem().toString(), curso.getId());

			String valueRowDisciplina = (String) tabela.getValueAt(i, 1);
			disc = aM.pegarDisciplinaPorNome(valueRowDisciplina);
			String valueRowProf = (String) tabela.getValueAt(i, 2);
			prof = aM.pegarProfessorPorNome(valueRowProf);

			grade.setId_disciplina(disc.getId());
			grade.setId_professor(prof.getId());
			grade.setId_fase(fase.getId());
			
			if(this.grade.getId() != null && i == 0) {
				
				grade.setId(this.grade.getId());
				
				aM.editarDado(grade);
				
				notifyObservers(grade);
				
			} else {
				aM.inserirDado(grade);
			}
		}
		
		if(this.grade.getId() == null) {
			JOptionPane.showMessageDialog(null, "Grade cadastrada com sucesso!");
		} else {
			// Reseta a lista e atualiza JTable novamente para o caso de haver uma nova adi�ao de grade na tela de edi��o.
			modelGradesEdicao.limpar();

			try {
				listaGrades = aM.pegarGrades();
				modelGradesEdicao.addListaDeGrades(listaGrades);
			} catch (Exception e2) {
				System.err.printf("Erro ao iniciar lista de Grades: %s.\n", e2.getMessage());
			}
			
			JOptionPane.showMessageDialog(null, "Grade salva com sucesso!");
			setVisible(false);
		}
	}

	// Obtem lista de Cursos
	private List<String> opcoesCursos(List<Curso> cursos) {
		return cursos.stream().map(curso -> curso.getCurso()).distinct().collect(Collectors.toList());
	}

	// Obtem lista de Fases referente ao curso
	private List<String> opcoesFases(List<Fase> fases, String curso) {

		Curso _curso = aM.pegarCursoPorNome(curso);
		int idCurso = _curso.getId();

		return fases.stream().filter(fase -> idCurso == fase.getIdCurso()).map(fase -> fase.getFase()).distinct()
				.collect(Collectors.toList());
	}

	// Obtem lista de Disciplinas
	private List<String> opcoesDisciplinas(List<Disciplina> disciplinas) {
		return disciplinas.stream().map(disciplina -> disciplina.getDisciplina()).distinct()
				.collect(Collectors.toList());
	}

	// Obtem lista de Professores
	private List<String> opcoesProfessores(List<Professor> professores) {
		return professores.stream().map(professor -> professor.getProfessor()).distinct().collect(Collectors.toList());
	}

	// Manipula��o da JTable

	// TODO:Organizar manipula��o com Table a uma classe DisciplinasTableModel

	public void addListaJTable(String disciplina, String professor) {
		disc = aM.pegarDisciplinaPorNome(disciplina);
		prof = aM.pegarProfessorPorNome(professor);

		String codDisciplina = String.valueOf(disc.getCodDisciplina());
		String[] linha = { codDisciplina, disc.getDisciplina(), prof.getProfessor() };
		criarGrid(linha);
	}

	public void criarGrid(String[] linha) {
		painel = new JPanel();
		getContentPane().add(painel);
		// Colunas da Grid

		String[] colunas = { "Codigo da disciplina", "Disciplina", "Professor" };

		model.setColumnIdentifiers(colunas);
		model.insertRow(numRow, linha);
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

		numRow = 0;
	}

	public void limparFormulario() {
		cbxDisciplina.setSelectedIndex(0);
		cbxProfessor.setSelectedIndex(0);
	}
	
	private void setarValores(Grade grade) {
		// TODO: setar valores iniciais para edi��o
		this.grade.setId(grade.getId());
		
		if(grade.getDescricaoCurso() != null) {
			cbxCurso.setSelectedItem(grade.getDescricaoCurso());
		}
		
		if(grade.getDescricaoDisciplina() != null) {
			cbxDisciplina.setSelectedItem(grade.getDescricaoDisciplina());
		}
		
		if(grade.getDescricaoFase() != null) {
			cbxFases.setSelectedItem(grade.getDescricaoFase());
		}
		
		if(grade.getDescricaoProfessor() != null) {
			cbxProfessor.setSelectedItem(grade.getDescricaoProfessor());
		}
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
		Iterator it = observers.iterator();
		while (it.hasNext()) {
			ObserverGrade observer = (ObserverGrade) it.next();
			observer.update(grade);
		}
	}
}
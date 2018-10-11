package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class CadastrarGradeWindow extends AbstractWindowFrame {

	private static final long serialVersionUID = 10914486141164967L;

	ManipularArquivo aM = new ManipularArquivo();
	DefaultTableModel model = new DefaultTableModel();
	private JLabel labes;
	private JComboBox<String> cbxCurso, cbxFases;
	JTable tabela;
	List<Curso> curso;
	List<Fase> fase;
	List<Disciplina> disciplina;
	List<Professor> professor;
	int numRow = 0;

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
		curso = aM.pegarCurso();
		fase = aM.pegarFase();
		disciplina = aM.pegarDisciplinas();
		professor = aM.pegarProfessor();
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

				// Adiciona opções estados
				opcoesFases(fase, cursoSelecionado).forEach(fase -> cbxFases.addItem(fase));
				//TODO: Atualizar ComboBox ao voltar na opção --Selecione-- do Curso
				//TODO:Adicionar disciplinas ao JTable, se já cadastradas
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
					addListaJTable(cbxDisciplina.getSelectedItem().toString(), cbxProfessor.getSelectedItem().toString());
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
				cadastrarGrade();
				limparJTable();
			}
		});
	}

	public boolean validarCamposObrigatorios() {
		if (cbxDisciplina.getSelectedItem().toString().isEmpty() || cbxProfessor.getSelectedItem().toString().isEmpty()) {
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

			aM.inserirDado(grade);
		}
		JOptionPane.showMessageDialog(null, "Grade cadastrada com sucesso!");
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
		return disciplinas.stream().map(disciplina -> disciplina.getDisciplina()).distinct().collect(Collectors.toList());
	}
	
	// Obtem lista de Professores
	private List<String> opcoesProfessores(List<Professor> professores) {
		return professores.stream().map(professor -> professor.getProfessor()).distinct().collect(Collectors.toList());
	}
	
	// Manipulação da JTable

	//TODO:Organizar manipulação com Table a uma classe DisciplinasTableModel
	
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

}

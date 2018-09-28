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
import br.com.sistemaescolar.model.Professor;

public class CadastrarDisciplinasWindow extends AbstractWindowFrame {

	private static final long serialVersionUID = 10914486141164967L;

	ManipularArquivo aM = new ManipularArquivo();
	DefaultTableModel model = new DefaultTableModel();
	private JLabel labes;
	private JComboBox<String> cbxCurso, cbxFases;
	JTable tabela;
	List<Curso> curso;
	List<Fase> fase;
	int numRow = 0;

	private JTextField txfCod, txfDisciplina, txfProfessor;
	private JButton btnAdd, btnSalvar1;
	private JLabel label;

	private JPanel painel;
	private JScrollPane scrollpane;

	public CadastrarDisciplinasWindow() {
		super("Cadastrar Disciplina");
		curso = aM.pegarCurso();
		fase = aM.pegarFase();
		criarComponentes();
		criarGrid(null);
	}

	public void criarComponentes() {

		labes = new JLabel("Fase:");
		labes.setBounds(410, 60, 250, 25);
		getContentPane().add(labes);

		cbxFases = new JComboBox<String>();
		cbxFases.addItem("-Selecione-");
		cbxFases.setBounds(470, 60, 200, 25);
		cbxFases.setToolTipText("Informe a fase");
		getContentPane().add(cbxFases);
		
		labes = new JLabel("Curso:");
		labes.setBounds(410, 20, 250, 25);
		getContentPane().add(labes);

		cbxCurso = new JComboBox<String>();
		cbxCurso.addItem("-Selecione-");
		opcoesCursos(curso).forEach(cursos -> cbxCurso.addItem(cursos));
		cbxCurso.setBounds(470, 20, 200, 25);
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

		label = new JLabel("Código da disciplina");
		label.setBounds(410, 120, 130, 45);
		getContentPane().add(label);

		txfCod = new JTextField();
		txfCod.setBounds(410, 160, 115, 25);
		getContentPane().add(txfCod);

		label = new JLabel("Disciplina:");
		label.setBounds(560, 120, 130, 45);
		getContentPane().add(label);

		txfDisciplina = new JTextField();
		txfDisciplina.setBounds(670, 130, 150, 25);
		getContentPane().add(txfDisciplina);

		label = new JLabel("Professor:");
		label.setBounds(560, 150, 70, 45);
		getContentPane().add(label);

		txfProfessor = new JTextField();
		txfProfessor.setBounds(670, 160, 150, 25);
		getContentPane().add(txfProfessor);

		btnAdd = new JButton("+");
		btnAdd.setBounds(959, 160, 50, 25);
		getContentPane().add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validarCamposObrigatorios()) {
					JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para cadastrar!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {
					addListaJTable(txfCod.getText(), txfDisciplina.getText(), txfProfessor.getText());
					limparFormulario();
					numRow++;
				}
			}
		});

		btnSalvar1 = new JButton("Salvar");
		btnSalvar1.setBounds(900, 340, 110, 25);
		getContentPane().add(btnSalvar1);
		btnSalvar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarDisciplinas();
				limparJTable();
			}
		});
	}

	public boolean validarCamposObrigatorios() {
		if (txfCod.getText().isEmpty() || txfDisciplina.getText().isEmpty() || txfProfessor.getText().isEmpty()) {
			return true;
		}
		return false;
	}

	public void cadastrarDisciplinas() {

		Disciplina disciplina = new Disciplina();

		for (int i = 0; i < numRow; i++) {
			Curso curso = aM.pegarCursoPorNome(cbxCurso.getSelectedItem().toString());
			Fase fase = aM.pegarFasePorFaseCurso(cbxFases.getSelectedItem().toString(), curso.getId());

			String valueRowDisciplina = (String) tabela.getValueAt(i, 1);
			String valueRowCod = (String) tabela.getValueAt(i, 0);

			disciplina.setDisciplina(valueRowDisciplina);
			disciplina.setCodDisciplina(Integer.parseInt(valueRowCod));
			disciplina.setIdFase(fase.getId());

			aM.inserirDado(disciplina);
		}

		cadastrarProfessor();
	}

	public void cadastrarProfessor() {
		Professor prof = new Professor();
		Disciplina _disciplina = new Disciplina();

		for (int j = 0; j < numRow; j++) {
			String valueRowDisciplina = (String) tabela.getValueAt(j, 1);
			_disciplina = aM.pegarDisciplinaPorNome(valueRowDisciplina);

			String valueRow = (String) tabela.getValueAt(j, 2);
			prof.setProfessor(valueRow);
			prof.setIdDisciplina(_disciplina.getId());
			aM.inserirDado(prof);
		}
		JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
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

	// Manipulação da JTable

	//TODO:Organizar manipulação com Table a uma classe DisciplinasTableModel
	
	public void addListaJTable(String cod, String disciplina, String professor) {
		String[] linha = { cod, disciplina, professor };
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
		scrollpane.setBounds(410, 190, 600, 135);
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
		txfCod.setText("");
		txfDisciplina.setText("");
		txfProfessor.setText("");
	}

}

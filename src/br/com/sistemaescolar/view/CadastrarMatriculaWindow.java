package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import br.com.sistemaescolar.enums.DiasSemana;
import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Aluno;
import br.com.sistemaescolar.model.Curso;
import br.com.sistemaescolar.model.Disciplina;
import br.com.sistemaescolar.model.Fase;
import br.com.sistemaescolar.model.Grade;
import br.com.sistemaescolar.model.GradeItem;
import br.com.sistemaescolar.model.Matricula;
import br.com.sistemaescolar.model.MatriculaItem;
import br.com.sistemaescolar.model.Professor;
import br.com.sistemaescolar.table.model.GradeItemTableModel;
import br.com.sistemaescolar.table.model.MatriculaItemTableModel;

public class CadastrarMatriculaWindow extends AbstractWindowFrame {

	private static final long serialVersionUID = 1L;

	ManipularArquivo mA = new ManipularArquivo();

	private List<Curso> listaCursos;
	private List<Fase> listaFases;
	private List<Grade> listaGrade;

	private JPanel painel;
	private JTextField txfNumeroMatricula;
	private JComboBox<String> cbxAluno;
	private JComboBox<String> cbxDisciplina;
	private JComboBox<String> cbxCurso;
	private JComboBox<String> cbxFase;
	private JFormattedTextField ftxfDataMatricula;
	private JFormattedTextField ftxfDiaVencimento;
	private JButton btnAdd;
	private JButton btnRemover;
	private JButton btnSalvar;
	private JLabel labes;
	private List<Aluno> listaAlunos;
	private int numeroMatricula;
	
	Matricula matricula = new Matricula();

	private MatriculaItemTableModel modelMatriculaAlunos = new MatriculaItemTableModel();
	private JTable gridDisciplinas;
	private JScrollPane scrollpaneGridDisciplinas;

	private int linhaSelecionada;

	public CadastrarMatriculaWindow() {
		super("Cadastrar Matricula");
		inicializar();
	}

	private void inicializar() {
		listaCursos = mA.pegarCursos();
		listaFases = mA.pegarFases();
		listaGrade = mA.pegarGrades(true);
		this.listaAlunos = mA.pegarAlunos();
		numeroMatricula = mA.getMatricula();
		criarComponentes();
		criarGrid();
	}

	public void criarComponentes() {
		labes = new JLabel("Matricula:");
		labes.setBounds(410, 20, 250, 25);
		getContentPane().add(labes);

		txfNumeroMatricula = new JTextField();
		txfNumeroMatricula.setBounds(520, 20, 80, 25);
		txfNumeroMatricula.setText(Integer.toString(numeroMatricula));
		txfNumeroMatricula.setEnabled(false);
		getContentPane().add(txfNumeroMatricula);

		labes = new JLabel("Aluno:");
		labes.setBounds(670, 20, 250, 25);
		getContentPane().add(labes);

		cbxAluno = new JComboBox<String>();
		cbxAluno.setBounds(760, 20, 170, 25);
		cbxAluno.addItem("-Selecione-");
		getContentPane().add(cbxAluno);
		opcoesAlunos(listaAlunos).forEach(alunos -> cbxAluno.addItem(alunos));

		labes = new JLabel("Data de Matricula:");
		labes.setBounds(410, 60, 250, 25);
		getContentPane().add(labes);

		try {
			ftxfDataMatricula = new JFormattedTextField(new MaskFormatter("   ##/##/####"));
			ftxfDataMatricula.setFocusLostBehavior(JFormattedTextField.COMMIT);
			ftxfDataMatricula.setBounds(520, 60, 80, 25);
			ftxfDataMatricula.setToolTipText("Digite a data de matricula");
			getContentPane().add(ftxfDataMatricula);

			labes = new JLabel("Dia de vencimento do boleto:");
			labes.setBounds(670, 60, 250, 25);
			getContentPane().add(labes);

			ftxfDiaVencimento = new JFormattedTextField(new MaskFormatter("##"));
			ftxfDiaVencimento.setFocusLostBehavior(JFormattedTextField.COMMIT);
			ftxfDiaVencimento.setBounds(880, 60, 51, 25);
			ftxfDiaVencimento.setToolTipText("Digite o dia de vencimento");
			getContentPane().add(ftxfDiaVencimento);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		labes = new JLabel("Curso:");
		labes.setBounds(410, 130, 130, 45);
		getContentPane().add(labes);

		cbxCurso = new JComboBox<String>();
		cbxCurso.addItem("-Selecione-");
		opcoesCursos(listaCursos).forEach(cursos -> cbxCurso.addItem(cursos));
		cbxCurso.setBounds(480, 140, 170, 25);
		getContentPane().add(cbxCurso);

		labes = new JLabel("Fase:");
		labes.setBounds(410, 160, 70, 45);
		getContentPane().add(labes);

		cbxFase = new JComboBox<String>();
		cbxFase.addItem("-Selecione-");
		cbxFase.setBounds(480, 170, 170, 25);
		getContentPane().add(cbxFase);

		cbxCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cursoSelecionado = cbxCurso.getSelectedItem().toString();

				cbxFase.removeAllItems();
				cbxFase.addItem("-Selecione-");
				cbxDisciplina.removeAllItems();
				cbxDisciplina.addItem("-Selecione-");

				if (cursoSelecionado == null || cursoSelecionado.equals("-Selecione-")) {
					return;
				}

				opcoesFases(listaFases, cursoSelecionado).forEach(fase -> cbxFase.addItem(fase));
			}
		});

		cbxFase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Curso curso = mA.pegarCursoPorNome(cbxCurso.getSelectedItem().toString());
				String faseSelecionada = (String) cbxFase.getSelectedItem();

				cbxDisciplina.removeAllItems();
				cbxDisciplina.addItem("-Selecione-");

				if (faseSelecionada == null || faseSelecionada.equals("-Selecione-")) {
					return;
				}

				List<HashSet<GradeItem>> listaGradeItem = opcoesDisciplinas(listaGrade, faseSelecionada, curso);
				HashSet<GradeItem> HashSetGradeItem = listaGradeItem.get(0);
				for (GradeItem gradeItem : HashSetGradeItem) {
					cbxDisciplina.addItem(gradeItem.getDisciplina().getNome());
				}
			}
		});

		labes = new JLabel("Disciplina:");
		labes.setBounds(670, 160, 130, 45);
		getContentPane().add(labes);

		cbxDisciplina = new JComboBox<String>();
		cbxDisciplina.addItem("-Selecione-");
		cbxDisciplina.setBounds(760, 170, 170, 25);
		getContentPane().add(cbxDisciplina);

		btnAdd = new JButton("+");
		btnAdd.setBounds(959, 170, 50, 25);
		getContentPane().add(btnAdd);

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validarCamposObrigatorios()) {
					JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para cadastrar!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {
					if (addGridItem(cbxCurso.getSelectedItem().toString(),
							cbxFase.getSelectedItem().toString(),
							cbxDisciplina.getSelectedItem().toString())) {
						limparSeletoresDisciplina();
					}else {
						JOptionPane.showMessageDialog(rootPane, "Item já esta cadastrado na grade!", "",
								JOptionPane.ERROR_MESSAGE, null);
					}
				}
			}
		});

		btnRemover = new JButton("-");
		btnRemover.setBounds(1019, 170, 50, 25);
		btnRemover.setEnabled(false);
		getContentPane().add(btnRemover);

		btnRemover.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removerLinha();
				btnRemover.setEnabled(false);
			}
		});

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(960, 350, 110, 25);
		getContentPane().add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modelMatriculaAlunos.getRowCount() == 0) {
					JOptionPane.showMessageDialog(rootPane, "Informe as disciplinas para cadastro!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {
					 salvarGrade();
				}
			}
		});

	}
	
	private void salvarGrade() {
		System.out.println(cbxAluno.getSelectedItem().toString());
		Aluno aluno = mA.pegarAlunoPorNome(cbxAluno.getSelectedItem().toString());
		
		System.out.println(aluno);
		
		matricula.setAluno(aluno);
		matricula.setData(ftxfDataMatricula.getText());
		matricula.setDiaVencimento(Integer.parseInt(ftxfDiaVencimento.getText()));
		
		matricula.clearItens();
		
		// Percorre os items da grid
		modelMatriculaAlunos.getItems().forEach(matriculaItem -> {
			// Insere os items no objeto grade
			matricula.setItem(matriculaItem);
		});

		// Cadastro
		if (matricula.getId() == null) {
			mA.inserirDado(matricula);

			limparFormulario();
			JOptionPane.showMessageDialog(null, "Matricula salva com sucesso!");
		}
	}

	private void criarGrid() {
		painel = new JPanel();
		getContentPane().add(painel);

		gridDisciplinas = new JTable(modelMatriculaAlunos);
		gridDisciplinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Ação Seleção de uma linha
		gridDisciplinas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				btnRemover.setEnabled(true);
				if (gridDisciplinas.getSelectedRow() != -1) {
					linhaSelecionada = gridDisciplinas.getSelectedRow();
				}
			}
		});

		scrollpaneGridDisciplinas = new JScrollPane(gridDisciplinas);
		scrollpaneGridDisciplinas.setBounds(410, 200, 660, 135);
		setLayout(null);
		scrollpaneGridDisciplinas.setVisible(true);
		add(scrollpaneGridDisciplinas);
	}
	
	public boolean validarCamposObrigatorios() {
		if ("-Selecione-".equals(cbxAluno.getSelectedItem())
				|| "".equals(ftxfDataMatricula.getText())
				|| "".equals(ftxfDataMatricula.getText())) {

			return true;
		}
		return false;
	}

	// Obtem lista de Alunos
	private List<String> opcoesAlunos(List<Aluno> alunos) {
		return alunos.stream().map(aluno -> aluno.getNomeAluno()).distinct().collect(Collectors.toList());
	}

	// Obtem lista de Cursos
	private List<String> opcoesCursos(List<Curso> cursos) {
		return cursos.stream().map(curso -> curso.getNome()).distinct().collect(Collectors.toList());
	}

	// Obtem lista de Fases referente ao curso
	private List<String> opcoesFases(List<Fase> fases, String nomeCurso) {
		Curso curso = mA.pegarCursoPorNome(nomeCurso);

		if (curso != null) {
			return fases.stream().filter(fase -> curso.getId() == fase.getCurso().getId()).map(fase -> fase.getNome())
					.distinct().collect(Collectors.toList());
		}

		return null;
	}

	// Obtem lista de Disciplinas referente a fase do curso
	private List<HashSet<GradeItem>> opcoesDisciplinas(List<Grade> grades, String nomeFase, Curso curso) {
		Fase fase = mA.pegarFasePorNomeFaseIdCurso(nomeFase, curso.getId());

		if (fase != null) {
			return grades.stream().filter(grade -> fase.getId() == grade.getFase().getId())
					.map(grade -> grade.getItens()).distinct().collect(Collectors.toList());
		}

		return null;
	}
	
	private boolean addGridItem(String nomeCurso, String nomeFase,String nomeDisciplina) {
		Curso curso = mA.pegarCursoPorNome(nomeCurso);
		Fase fase = mA.pegarFasePorNomeFaseIdCurso(nomeFase, curso.getId());
		Disciplina disciplina = mA.pegarDisciplinaPorNome(nomeDisciplina);

		MatriculaItem item = new MatriculaItem(null,null,curso,fase,disciplina);

		return adicionarItemGrid(item);
	}

	private boolean adicionarGradeItem(Curso curso, Fase fase, Disciplina disciplina) {
		MatriculaItem item = new MatriculaItem(null, null, curso, fase, disciplina);

		return adicionarItemGrid(item);
	}

	private boolean adicionarItemGrid(MatriculaItem item) {
		if (verificaItemDuplicado(item)) {
			return false;
		}
		modelMatriculaAlunos.addItem(item);
		return true;
	}

	public void limparGrid() {
		modelMatriculaAlunos.limpar();
		novaMatricula();
	}

	private void novaMatricula() {
		cbxAluno.setSelectedIndex(0);
		ftxfDataMatricula.setText("");
		ftxfDiaVencimento.setText("");
		
		numeroMatricula = mA.getMatricula();
		txfNumeroMatricula.setText(Integer.toString(numeroMatricula));
		matricula = new Matricula();
	}
	
	private void limparSeletoresDisciplina() {
		cbxDisciplina.setSelectedIndex(0);
		cbxCurso.setSelectedIndex(0);
		cbxFase.setSelectedIndex(0);	
	}

	public void limparFormulario() {
		limparSeletoresDisciplina();
		limparGrid();
	}

	// Remove linha da grid
	public void removerLinha() {
		modelMatriculaAlunos.removeItem(linhaSelecionada);
	}

	private void setarValores(Matricula matricula) {
		// Preenche a grid com os items
		 matricula.getItens().forEach(gradeItem ->
		 adicionarGradeItem(gradeItem.getCurso(), gradeItem.getFase(),
		 gradeItem.getDisciplina()));
	}

	public boolean verificaItemDuplicado(MatriculaItem item) {

		String linhaItem = Integer.toString(item.getCurso().getId() + item.getFase().getId() + item.getDisciplina().getId());

		for (int i = 0; i < modelMatriculaAlunos.getRowCount(); i++) {
			MatriculaItem itemGrade = modelMatriculaAlunos.getItem(i);
			String linhaItemGrade = Integer.toString(itemGrade.getCurso().getId() + itemGrade.getFase().getId()
					+ itemGrade.getDisciplina().getId());

			if (linhaItem.equals(linhaItemGrade)) {
				return true;
			}

		}
		return false;
	}

}

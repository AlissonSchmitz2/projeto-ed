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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Curso;
import br.com.sistemaescolar.model.Disciplina;
import br.com.sistemaescolar.model.Fase;
import br.com.sistemaescolar.model.Grade;
import br.com.sistemaescolar.model.GradeItem;
import br.com.sistemaescolar.model.Professor;
import br.com.sistemaescolar.observer.ObserverGrade;
import br.com.sistemaescolar.observer.SubjectGrade;
import br.com.sistemaescolar.table.model.GradeItemTableModel;

public class CadastrarGradeWindow extends AbstractWindowFrame implements SubjectGrade {

	private static final long serialVersionUID = 10914486141164967L;

	private JPanel painel;
	private JLabel labes;
	private JComboBox<String> cbxCurso, cbxFases;

	private List<Curso> listaCursos;
	private List<Fase> listaFases;
	private List<Disciplina> listaDisciplinas;
	private List<Professor> listaProfessores;

	private ArrayList<ObserverGrade> observers = new ArrayList<ObserverGrade>();
	private JComboBox<String> cbxDisciplina;
	private JComboBox<String> cbxProfessor;
	private JComboBox<String> cbxDiaSemana;
	private JButton btnAdd, btnSalvar, btnRemover;
	private JLabel label;

	private Grade grade = new Grade();

	private GradeItemTableModel modelGridDisciplinas = new GradeItemTableModel();
	private JTable gridDisciplinas;
	private JScrollPane scrollpaneGridDisciplinas;

	private int linhaSelecionada;

	ManipularArquivo aM = new ManipularArquivo();

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
		criarGrid();
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
		label.setBounds(410, 160, 70, 45);
		getContentPane().add(label);

		cbxProfessor = new JComboBox<String>();
		cbxProfessor.setBounds(480, 170, 170, 25);
		getContentPane().add(cbxProfessor);
		cbxProfessor.addItem("-Selecione-");
		opcoesProfessores(listaProfessores).forEach(professores -> cbxProfessor.addItem(professores));

		label = new JLabel("Dia da Semana:");
		label.setBounds(670, 160, 130, 45);
		getContentPane().add(label);

		cbxDiaSemana = new JComboBox<String>();
		cbxDiaSemana.addItem("-Selecione-");
		cbxDiaSemana.addItem("01-Domingo");
		cbxDiaSemana.addItem("02-Segunda-Feira");
		cbxDiaSemana.addItem("03-Terça-Feira");
		cbxDiaSemana.addItem("04-Quarta-Feira");
		cbxDiaSemana.addItem("05-Quinta-Feira");
		cbxDiaSemana.addItem("06-Sexta-Feira");
		cbxDiaSemana.addItem("07-Sábado");
		cbxDiaSemana.setBounds(770, 170, 170, 25);
		getContentPane().add(cbxDiaSemana);

		btnAdd = new JButton("+");
		btnAdd.setBounds(959, 170, 50, 25);
		getContentPane().add(btnAdd);

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validarCamposObrigatorios()) {
					JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para cadastrar!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {
					if (addGridItem(cbxDisciplina.getSelectedItem().toString(),
							cbxProfessor.getSelectedItem().toString())) {
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
				if (modelGridDisciplinas.getRowCount() == 0) {
					JOptionPane.showMessageDialog(rootPane, "Informe as disciplinas para cadastro!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {
					salvarGrade();
				}
			}
		});
	}

	public boolean validarCamposObrigatorios() {
		if ("-Selecione-".equals(cbxDisciplina.getSelectedItem())
				|| "-Selecione-".equals(cbxProfessor.getSelectedItem())
				|| "-Selecione-".equals(cbxCurso.getSelectedItem())
				|| "-Selecione-".equals(cbxFases.getSelectedItem())) {

			return true;
		}
		return false;
	}

	private void salvarGrade() {
		Curso curso = aM.pegarCursoPorNome(cbxCurso.getSelectedItem().toString());
		Fase fase = aM.pegarFasePorNomeFaseIdCurso(cbxFases.getSelectedItem().toString(), curso.getId());

		// Antes de continuar, verifica se já não existe um grade para o curso e fase
		Grade gradeExistente = aM.pegarGradePorIdFase(fase.getId(), false);
		if ((grade.getId() == null && gradeExistente != null) || (grade.getId() != null && gradeExistente != null
				&& gradeExistente.getId().compareTo(grade.getId()) != 0)) {
			JOptionPane.showMessageDialog(rootPane, "Já existe uma grade para o curso e fase selecionados!", "",
					JOptionPane.ERROR_MESSAGE, null);

			return;
		}

		grade.setFase(fase);
		grade.clearItens();

		// Percorre os items da grid
		modelGridDisciplinas.getItems().forEach(gradeItem -> {
			// Insere os items no objeto grade
			grade.setItem(gradeItem);
		});

		// Edição
		if (grade.getId() != null) {
			aM.editarDado(grade);

			notifyObservers(grade);
			JOptionPane.showMessageDialog(null, "Grade salva com sucesso!");

			limparFormulario();
			setVisible(false);
		}

		// Cadastro
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
	private List<String> opcoesFases(List<Fase> fases, String nomeCurso) {
		Curso curso = aM.pegarCursoPorNome(nomeCurso);

		return fases.stream().filter(fase -> curso.getId() == fase.getCurso().getId()).map(fase -> fase.getNome())
				.distinct().collect(Collectors.toList());
	}

	// Obtem lista de Disciplinas
	private List<String> opcoesDisciplinas(List<Disciplina> disciplinas) {
		return disciplinas.stream().map(disciplina -> disciplina.getNome()).distinct().collect(Collectors.toList());
	}

	// Obtem lista de Professores
	private List<String> opcoesProfessores(List<Professor> professores) {
		return professores.stream().map(professor -> professor.getNome()).distinct().collect(Collectors.toList());
	}

	private boolean addGridItem(String nomeDisciplina, String nomeProfessor) {
		Disciplina disciplina = aM.pegarDisciplinaPorNome(nomeDisciplina);
		Professor professor = aM.pegarProfessorPorNome(nomeProfessor);
		String codDiaSemana = "0" + cbxDiaSemana.getSelectedIndex();

		GradeItem item = new GradeItem(null, null, disciplina, professor, codDiaSemana);

		return adicionarItemGrid(item);
	}

	private boolean adicionarGradeItem(Disciplina disciplina, Professor professor) {
		String codDiaSemana = "0" + cbxDiaSemana.getItemCount();
		GradeItem item = new GradeItem(null, null, disciplina, professor, codDiaSemana);

		return adicionarItemGrid(item);
	}

	private boolean adicionarItemGrid(GradeItem item) {
		// TODO: validar se algum item duplicado esta sendo inserido
		if (verificaItemDuplicado(item)) {
			return false;
		}
		modelGridDisciplinas.addItem(item);

		return true;
	}

	private void criarGrid() {
		painel = new JPanel();
		getContentPane().add(painel);

		gridDisciplinas = new JTable(modelGridDisciplinas);
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

	public void limparGrid() {
		modelGridDisciplinas.limpar();
	}

	private void limparSeletoresDisciplina() {
		cbxDisciplina.setSelectedIndex(0);
		cbxProfessor.setSelectedIndex(0);
		cbxDiaSemana.setSelectedIndex(0);
	}

	public void limparFormulario() {
		// TODO: limpar os campos da grade (exceto seletores da grid pois já são limpos
		// ao inserir)
		limparSeletoresDisciplina();
		limparGrid();
	}

	public void removerLinha() {
		modelGridDisciplinas.removeItem(linhaSelecionada);
	}

	private void setarValores(Grade grade) {
		cbxCurso.setSelectedItem(grade.getFase().getCurso().getNome());

		cbxFases.setSelectedItem(grade.getFase().getNome());

		// Preenche a grid com os items
		grade.getItens().forEach(gradeItem -> adicionarGradeItem(gradeItem.getDisciplina(), gradeItem.getProfessor()));
	}

	public boolean verificaItemDuplicado(GradeItem item) {

		String linhaItem = item.getDisciplina().getId() + item.getCodigoDiaSemana() + item.getProfessor().getId();

		for (int i = 0; i < modelGridDisciplinas.getRowCount(); i++) {
			GradeItem itemGrade = modelGridDisciplinas.getItem(i);
			String linhaItemGrade = itemGrade.getDisciplina().getId() + itemGrade.getCodigoDiaSemana()
					+ itemGrade.getProfessor().getId();

			if (linhaItem.equals(linhaItemGrade)) {
				return true;
			}

		}
		return false;
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

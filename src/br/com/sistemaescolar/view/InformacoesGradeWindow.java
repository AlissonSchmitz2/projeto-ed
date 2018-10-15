package br.com.sistemaescolar.view;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import br.com.sistemaescolar.model.Disciplina;
import br.com.sistemaescolar.model.Grade;
import br.com.sistemaescolar.model.GradeItem;
import br.com.sistemaescolar.model.Professor;
import br.com.sistemaescolar.table.model.GradeItemTableModel;

public class InformacoesGradeWindow extends AbstractWindowFrame {
	private static final long serialVersionUID = 4378581022091507185L;
	
	private JPanel painel;
	private JLabel labes;
	
	private JTextField txfFase;
	private JTextField txfCurso;
	private Grade grade = new Grade();

	private GradeItemTableModel modelGridDisciplinas = new GradeItemTableModel();
	private JTable gridDisciplinas;
	private JScrollPane scrollpaneGridDisciplinas;

	public InformacoesGradeWindow(Grade grade) {
		super("Informações da Grade");		
		
		this.grade = grade;
		
		criarComponentes();
		setarValores(grade);
		criarGrid();
	}

	public void criarComponentes() {

		labes = new JLabel("Fase:");
		labes.setBounds(410, 60, 250, 25);
		getContentPane().add(labes);

		txfFase = new JTextField();
		txfFase.setBounds(455, 60, 170, 25);
		txfFase.setBackground(Color.WHITE);
		txfFase.setEditable(false);
		getContentPane().add(txfFase);

		labes = new JLabel("Curso:");
		labes.setBounds(410, 20, 250, 25);
		getContentPane().add(labes);

		txfCurso = new JTextField();
		txfCurso.setBounds(455, 20, 170, 25);
		txfCurso.setBackground(Color.WHITE);
		txfCurso.setEditable(false);
		getContentPane().add(txfCurso);
		
	}
	

	private void criarGrid() {
		
		painel = new JPanel();
		getContentPane().add(painel);

		gridDisciplinas = new JTable(modelGridDisciplinas);
		gridDisciplinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollpaneGridDisciplinas = new JScrollPane(gridDisciplinas);
		scrollpaneGridDisciplinas.setBounds(410, 100, 660, 135);
		setLayout(null);
		scrollpaneGridDisciplinas.setVisible(true);
		add(scrollpaneGridDisciplinas);
		
	}	

	private void setarValores(Grade grade) {
		
		txfCurso.setText(grade.getFase().getCurso().getNome());

		txfFase.setText(grade.getFase().getNome());

		// Preenche a grid com os items
		grade.getItens().forEach(gradeItem -> adicionarGradeItem(gradeItem.getDisciplina(), gradeItem.getProfessor(), gradeItem.getCodigoDiaSemana()));
	
	}
	
	private boolean adicionarGradeItem(Disciplina disciplina, Professor professor, String codDiaSemana) {
				
		GradeItem item = new GradeItem(null, null, disciplina, professor, codDiaSemana);

		return adicionarItemGrid(item);
	}

	private boolean adicionarItemGrid(GradeItem item) {
		
		if (verificaItemDuplicado(item)) {
			return false;
		}
		
		modelGridDisciplinas.addItem(item);

		return true;
		
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
	
}

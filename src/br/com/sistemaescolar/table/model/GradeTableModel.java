package br.com.sistemaescolar.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.sistemaescolar.model.Grade;

public class GradeTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -4647244243422437228L;
	
	private List<Grade> grades;
	private String[] colunas = new String[] { "ID", "Curso", "Fase", "Disciplina", "Professor" };
	
	public GradeTableModel (List<Grade> grades) {
		this.grades = grades;
	}
	
	public GradeTableModel () {
		this.grades = new ArrayList<Grade>();
	}
	
	public int getRowCount() {
		return grades.size();
	}
	
	public int getColumnCount() {
		return colunas.length;
	}

	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
	
	public void setValueAt(Grade aValue, int rowIndex) {
		Grade grade = grades.get(rowIndex);

		grade.setId(aValue.getId());
		grade.setDescricaoCurso(aValue.getDescricaoCurso());
		grade.setDescricaoFase(aValue.getDescricaoFase());
		grade.setDescricaoDisciplina(aValue.getDescricaoDisciplina());
		grade.setDescricaoProfessor(aValue.getDescricaoProfessor());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
		fireTableCellUpdated(rowIndex, 2);
		fireTableCellUpdated(rowIndex, 3);
		fireTableCellUpdated(rowIndex, 4);
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Grade grade = grades.get(rowIndex);

		switch (columnIndex) {
		case 0:
			grade.setId(Integer.parseInt(aValue.toString()));
		case 1:			
			grade.setDescricaoCurso(aValue.toString());
		case 2:
			grade.setDescricaoFase(aValue.toString());
		case 3:
			grade.setDescricaoDisciplina(aValue.toString());
		case 4:
			grade.setDescricaoProfessor(aValue.toString());

		default:
			System.err.println("Índice da coluna inválido");
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Grade gradeSelecionada = grades.get(rowIndex);
		String valueObject = null;
		
		switch (columnIndex) {
		case 0:
			valueObject = gradeSelecionada.getId().toString();
			break;
		case 1:
			valueObject = gradeSelecionada.getDescricaoCurso();
			break;
		case 2:
			valueObject = gradeSelecionada.getDescricaoFase();
			break;
		case 3:
			valueObject = gradeSelecionada.getDescricaoDisciplina();
			break;
		case 4:
			valueObject = gradeSelecionada.getDescricaoProfessor();
			break;
		default:
			System.err.println("Índice inválido para propriedade do bean Professor.class");
		}

		return valueObject;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public Grade getGrade(int indiceLinha) {
		return grades.get(indiceLinha);
	}
	
	public void addGrade(Grade g) {
		grades.add(g);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void removeGrade(int indiceLinha) {
		grades.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}
	
	public void addListaDeGrades(List<Grade> novasGrades) {

		int tamanhoAntigo = getRowCount();
		grades.addAll(novasGrades);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}
	
	public void limpar() {
		grades.clear();
		fireTableDataChanged();
	}
	
	public boolean isEmpty() {
		return grades.isEmpty();
	}
}

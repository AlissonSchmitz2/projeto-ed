package br.com.sistemaescolar.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.sistemaescolar.model.Matricula;

public class MatriculaTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -7672880562932393874L;

	private List<Matricula> matriculas;
	private String[] colunas = new String[] { "ID", "Aluno"};
	
	public MatriculaTableModel (List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}
	
	public MatriculaTableModel () {
		this.matriculas = new ArrayList<Matricula>();
	}
	
	public int getRowCount() {
		return matriculas.size();
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
	
	public void setValueAt(Matricula aValue, int rowIndex) {
		Matricula grade = matriculas.get(rowIndex);

		grade.setId(aValue.getId());
		grade.setAluno(aValue.getAluno());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Matricula grade = matriculas.get(rowIndex);

		switch (columnIndex) {
		case 0:
			grade.setId(Integer.parseInt(aValue.toString()));

		default:
			System.err.println("Índice da coluna inválido");
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Matricula gradeSelecionada = matriculas.get(rowIndex);
		String valueObject = null;
		
		switch (columnIndex) {
		case 0:
			valueObject = gradeSelecionada.getId().toString();
			break;
		default:
			System.err.println("Índice inválido para propriedade do bean Grade.class");
		}

		return valueObject;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public Matricula getMatricula(int indiceLinha) {
		return matriculas.get(indiceLinha);
	}
	
	public void addMatricula(Matricula d) {
		matriculas.add(d);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void removeMatricula(int indiceLinha) {
		matriculas.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}
	
	public void addListaDeMatricula(List<Matricula> novasGrades) {

		int tamanhoAntigo = getRowCount();
		matriculas.addAll(novasGrades);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}
	
	public void limpar() {
		matriculas.clear();
		fireTableDataChanged();
	}
	
	public boolean isEmpty() {
		return matriculas.isEmpty();
	}

}

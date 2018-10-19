package br.com.sistemaescolar.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.sistemaescolar.enums.DiasSemana;
import br.com.sistemaescolar.model.MatriculaItem;

public class MatriculaItemTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -1487051977919290609L;
	
	private List<MatriculaItem> matriculaItens;
	private String[] colunas = new String[] { "Curso", "Fase", "Disciplina"};
	
	public MatriculaItemTableModel (List<MatriculaItem> matriculaItens) {
		this.matriculaItens = matriculaItens;
	}
	
	public MatriculaItemTableModel () {
		this.matriculaItens = new ArrayList<MatriculaItem>();
	}
	
	public int getRowCount() {
		return matriculaItens.size();
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
	
	public void setValueAt(MatriculaItem aValue, int rowIndex) {
		MatriculaItem matriculaItem = matriculaItens.get(rowIndex);

		matriculaItem.getDisciplina().setCodDisciplina(aValue.getId());
		
		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
		fireTableCellUpdated(rowIndex, 2);
		fireTableCellUpdated(rowIndex, 3);
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		MatriculaItem matriculaItem = matriculaItens.get(rowIndex);

		switch (columnIndex) {
		case 0:
			matriculaItem.getDisciplina().setCodDisciplina(Integer.parseInt(aValue.toString()));

		default:
			System.err.println("Índice da coluna inválido");
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		MatriculaItem matriculaItemSelecionado = matriculaItens.get(rowIndex);
		String valueObject = null;
		
		switch (columnIndex) {
		case 0:
			valueObject = matriculaItemSelecionado.getCurso().getNome();
			break;
		case 1:
			valueObject = matriculaItemSelecionado.getFase().getNome();
			break;
		case 2:
			valueObject = matriculaItemSelecionado.getDisciplina().getNome();
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
	
	public List<MatriculaItem> getItems() {
		return matriculaItens;
	}
	
	public MatriculaItem getItem(int indiceLinha) {
		return matriculaItens.get(indiceLinha);
	}
	
	public void addItem(MatriculaItem item) {
		matriculaItens.add(item);
		
		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void removeItem(int indiceLinha) {
		matriculaItens.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}
	
	public void addItens(List<MatriculaItem> novasMatriculaItens) {

		int tamanhoAntigo = getRowCount();
		matriculaItens.addAll(novasMatriculaItens);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}
	
	public void limpar() {
		matriculaItens.clear();
		fireTableDataChanged();
	}
	
	public boolean isEmpty() {
		return matriculaItens.isEmpty();
	}

}

package br.com.sistemaescolar.table.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import br.com.sistemaescolar.enums.DiasSemana;
import br.com.sistemaescolar.model.GradeItem;

public class GradeItemTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -1487051977919290609L;
	
	private List<GradeItem> gradeItens;
	private String[] colunas = new String[] { "Código Disciplina", "Disciplina", "Professor", "Dia Semana" };
	
	public GradeItemTableModel (List<GradeItem> gradeItens) {
		this.gradeItens = gradeItens;
	}
	
	public GradeItemTableModel () {
		this.gradeItens = new ArrayList<GradeItem>();
	}
	
	public int getRowCount() {
		return gradeItens.size();
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
	
	public void setValueAt(GradeItem aValue, int rowIndex) {
		GradeItem gradeItem = gradeItens.get(rowIndex);

		gradeItem.getDisciplina().setCodDisciplina(aValue.getId());
		
		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
		fireTableCellUpdated(rowIndex, 2);
		fireTableCellUpdated(rowIndex, 3);
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		GradeItem gradeItem = gradeItens.get(rowIndex);

		switch (columnIndex) {
		case 0:
			gradeItem.getDisciplina().setCodDisciplina(Integer.parseInt(aValue.toString()));

		default:
			System.err.println("Índice da coluna inválido");
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		GradeItem gradeItemSelecionado = gradeItens.get(rowIndex);
		String valueObject = null;
		
		switch (columnIndex) {
		case 0:
			valueObject = gradeItemSelecionado.getDisciplina().getCodDisciplina().toString();
			break;
		case 1:
			valueObject = gradeItemSelecionado.getDisciplina().getNome();
			break;
		case 2:
			valueObject = gradeItemSelecionado.getProfessor().getNome();
			break;
		case 3:
			valueObject = DiasSemana.getDescriptionByCode(gradeItemSelecionado.getCodigoDiaSemana());
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
	
	public List<GradeItem> getItems() {
		return gradeItens;
	}
	
	public GradeItem getItem(int indiceLinha) {
		return gradeItens.get(indiceLinha);
	}
	
	public void addItem(GradeItem item) {
		gradeItens.add(item);
		
		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void removeItem(int indiceLinha) {
		gradeItens.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}
	
	public void addItens(List<GradeItem> novosGradeItens) {

		int tamanhoAntigo = getRowCount();
		gradeItens.addAll(novosGradeItens);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}
	
	public void limpar() {
		gradeItens.clear();
		fireTableDataChanged();
	}
	
	public boolean isEmpty() {
		return gradeItens.isEmpty();
	}
}

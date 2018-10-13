package br.com.sistemaescolar.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.sistemaescolar.model.Fase;

public class FaseTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -1794029882584300703L;

	private List<Fase> fases;
	private String[] colunas = new String[] { "ID", "Curso", "Fase" };
	
	public FaseTableModel (List<Fase> fases) {
		this.fases = fases;
	}
	
	public FaseTableModel () {
		this.fases = new ArrayList<Fase>();
	}
	
	public int getRowCount() {
		return fases.size();
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
	
	public void setValueAt(Fase aValue, int rowIndex) {
		Fase fase = fases.get(rowIndex);

		fase.setId(aValue.getId());

		fireTableCellUpdated(rowIndex, 0);
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Fase fase = fases.get(rowIndex);

		switch (columnIndex) {
		case 0:
			fase.setId(Integer.parseInt(aValue.toString()));

		default:
			System.err.println("Índice da coluna inválido");
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Fase faseSelecionada = fases.get(rowIndex);
		
		String valueObject = null;
		
		switch (columnIndex) {
		case 0:
			valueObject = faseSelecionada.getId().toString();
			break;
		case 1:
			valueObject = faseSelecionada.getCurso().getNome();
			break;
		case 2:
			valueObject = faseSelecionada.getNome();
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
	
	public Fase getFase(int indiceLinha) {
		return fases.get(indiceLinha);
	}
	
	public void addFase(Fase p) {
		fases.add(p);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void removeFase(int indiceLinha) {
		fases.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}
	
	public void addListaDeFases(List<Fase> novasFases) {

		int tamanhoAntigo = getRowCount();
		fases.addAll(novasFases);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}
	
	public void limpar() {
		fases.clear();
		fireTableDataChanged();
	}
	
	public boolean isEmpty() {
		return fases.isEmpty();
	}
	
}

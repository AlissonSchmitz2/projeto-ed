package br.com.sistemaescolar.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.sistemaescolar.model.Disciplina;

public class DisciplinaTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -7672880562932393874L;

	private List<Disciplina> disciplinas;
	private String[] colunas = new String[] { "ID", "Código", "Disciplina" };
	
	public DisciplinaTableModel (List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public DisciplinaTableModel () {
		this.disciplinas = new ArrayList<Disciplina>();
	}
	
	public int getRowCount() {
		return disciplinas.size();
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
	
	public void setValueAt(Disciplina aValue, int rowIndex) {
		Disciplina disciplina = disciplinas.get(rowIndex);

		disciplina.setId(aValue.getId());
		disciplina.setCodDisciplina(aValue.getCodDisciplina());
		disciplina.setDisciplina(aValue.getDisciplina());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
		fireTableCellUpdated(rowIndex, 2);
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Disciplina disciplina = disciplinas.get(rowIndex);

		switch (columnIndex) {
		case 0:
			disciplina.setId(Integer.parseInt(aValue.toString()));
		case 1:
			disciplina.setCodDisciplina(Integer.parseInt(aValue.toString()));
		case 2:
			disciplina.setDisciplina(aValue.toString());

		default:
			System.err.println("Índice da coluna inválido");
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Disciplina disciplinaSelecionada = disciplinas.get(rowIndex);
		String valueObject = null;
		
		switch (columnIndex) {
		case 0:
			valueObject = disciplinaSelecionada.getId().toString();
			break;
		case 1:
			valueObject = Integer.toString(disciplinaSelecionada.getCodDisciplina());
			break;
		case 2:
			valueObject = disciplinaSelecionada.getDisciplina();
			break;
		default:
			System.err.println("Índice inválido para propriedade do bean Disciplina.class");
		}

		return valueObject;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public Disciplina getDisciplina(int indiceLinha) {
		return disciplinas.get(indiceLinha);
	}
	
	public void addDisciplina(Disciplina d) {
		disciplinas.add(d);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void removeDisciplina(int indiceLinha) {
		disciplinas.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}
	
	public void addListaDeDisciplinas(List<Disciplina> novasDisciplinas) {

		int tamanhoAntigo = getRowCount();
		disciplinas.addAll(novasDisciplinas);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}
	
	public void limpar() {
		disciplinas.clear();
		fireTableDataChanged();
	}
	
	public boolean isEmpty() {
		return disciplinas.isEmpty();
	}

	
}

package table.model;

import javax.swing.table.AbstractTableModel;
import model.Cidade;
import java.util.ArrayList;
import java.util.List;

public class CidadeTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -3586211638575736174L;

	private List<Cidade> cidades;
	private String[] colunas = new String[] { "ID", "Cidade", "UF" };

	public CidadeTableModel(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public CidadeTableModel() {
		this.cidades = new ArrayList<Cidade>();
	}

	public int getRowCount() {
		return cidades.size();
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

	public void setValueAt(Cidade aValue, int rowIndex) {
		Cidade cidade = cidades.get(rowIndex);

		cidade.setId(aValue.getId());
		cidade.setCidade(aValue.getCidade());
		cidade.setUf(aValue.getUf());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
		fireTableCellUpdated(rowIndex, 2);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Cidade cidade = cidades.get(rowIndex);

		switch (columnIndex) {
		case 0:
			cidade.setId(Integer.parseInt(aValue.toString()));
		case 1:
			cidade.setCidade(aValue.toString());
		case 2:
			cidade.setUf(aValue.toString());

		default:
			System.err.println("Índice da coluna inválido");
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Cidade cidadeSelecionada = cidades.get(rowIndex);
		String valueObject = null;
		switch (columnIndex) {
		case 0:
			valueObject = cidadeSelecionada.getId().toString();
			break;
		case 1:
			valueObject = cidadeSelecionada.getCidade();
			break;
		case 2:
			valueObject = cidadeSelecionada.getUf();
			break;
		default:
			System.err.println("Índice inválido para propriedade do bean Cidade.class");
		}

		return valueObject;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Cidade getCidade(int indiceLinha) {
		return cidades.get(indiceLinha);
	}

	public void addCidade(Cidade u) {
		cidades.add(u);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void removeCidade(int indiceLinha) {
		cidades.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void addListaDeCidades(List<Cidade> novasCidades) {
		int tamanhoAntigo = getRowCount();
		cidades.addAll(novasCidades);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}

	public void limpar() {
		cidades.clear();
		fireTableDataChanged();
	}

	public boolean isEmpty() {
		return cidades.isEmpty();
	}

}
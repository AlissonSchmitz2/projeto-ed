package table.model;

import javax.swing.table.AbstractTableModel;
import model.Aluno;
import java.util.ArrayList;
import java.util.List;

public class AlunoTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -3586211638575736174L;

	private List<Aluno> alunos;
	private String[] colunas = new String[] { "ID", "Código", "Nome", "Email" };

	public AlunoTableModel(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public AlunoTableModel() {
		this.alunos = new ArrayList<Aluno>();
	}

	public int getRowCount() {
		return alunos.size();
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

	public void setValueAt(Aluno aValue, int rowIndex) {
		Aluno aluno = alunos.get(rowIndex);

		aluno.setId(aValue.getId());
		aluno.setCodAluno(aValue.getCodAluno());
		aluno.setNomeAluno(aValue.getNomeAluno());
		aluno.setEmail(aValue.getEmail());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
		fireTableCellUpdated(rowIndex, 2);
		fireTableCellUpdated(rowIndex, 3);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Aluno aluno = alunos.get(rowIndex);

		switch (columnIndex) {
		case 0:
			aluno.setId(Integer.parseInt(aValue.toString()));
		case 1:
			aluno.setCodAluno(aValue.toString());
		case 2:
			aluno.setNomeAluno(aValue.toString());
		case 3:
			aluno.setEmail(aValue.toString());

		default:
			System.err.println("Índice da coluna inválido");
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Aluno alunoSelecionado = alunos.get(rowIndex);
		String valueObject = null;
		switch (columnIndex) {
		case 0:
			valueObject = alunoSelecionado.getId().toString();
			break;
		case 1:
			valueObject = alunoSelecionado.getCodAluno();
			break;
		case 2:
			valueObject = alunoSelecionado.getNomeAluno();
			break;
		case 3:
			valueObject = alunoSelecionado.getEmail();
			break;
		default:
			System.err.println("Índice inválido para propriedade do bean Aluno.class");
		}

		return valueObject;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Aluno getAluno(int indiceLinha) {
		return alunos.get(indiceLinha);
	}

	public void addAluno(Aluno u) {
		alunos.add(u);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void removeAluno(int indiceLinha) {
		alunos.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void addListaDeAlunos(List<Aluno> novosAlunos) {

		int tamanhoAntigo = getRowCount();
		alunos.addAll(novosAlunos);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}

	public void limpar() {
		alunos.clear();
		fireTableDataChanged();
	}

	public boolean isEmpty() {
		return alunos.isEmpty();
	}

}
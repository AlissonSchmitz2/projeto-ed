package br.com.sistemaescolar.table.model;

import javax.swing.table.AbstractTableModel;
import br.com.sistemaescolar.model.Curso;
import java.util.ArrayList;
import java.util.List;

	public class CursosTableModel extends AbstractTableModel {
		private static final long serialVersionUID = -3586211638575736174L;

		private List<Curso> cursos;
		private String[] colunas = new String[] { "ID", "Curso" };

		public CursosTableModel(List<Curso> cursos) {
			this.cursos = cursos;
		}

		public CursosTableModel() {
			this.cursos = new ArrayList<Curso>();
		}

		public int getRowCount() {
			return cursos.size();
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

		public void setValueAt(Curso aValue, int rowIndex) {
			Curso curso = cursos.get(rowIndex);

			curso.setId(aValue.getId());
			curso.setCurso(aValue.getCurso());

			fireTableCellUpdated(rowIndex, 0);
			fireTableCellUpdated(rowIndex, 1);
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			Curso curso = cursos.get(rowIndex);

			switch (columnIndex) {
			case 0:
				curso.setId(Integer.parseInt(aValue.toString()));
			case 1:
				curso.setCurso(aValue.toString());
			default:
				System.err.println("Índice da coluna inválido");
			}
			fireTableCellUpdated(rowIndex, columnIndex);
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Curso cursoSelecionado = cursos.get(rowIndex);
			String valueObject = null;
			switch (columnIndex) {
			case 0:
				valueObject = cursoSelecionado.getId().toString();
				break;
			case 1:
				valueObject = cursoSelecionado.getCurso();
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

		public Curso getCurso(int indiceLinha) {
			return cursos.get(indiceLinha);
		}

		public void addAluno(Curso u) {
			cursos.add(u);

			int ultimoIndice = getRowCount() - 1;

			fireTableRowsInserted(ultimoIndice, ultimoIndice);
		}

		public void removeAluno(int indiceLinha) {
			cursos.remove(indiceLinha);

			fireTableRowsDeleted(indiceLinha, indiceLinha);
		}

		public void addListaDeCursos(List<Curso> novosCursos) {

			int tamanhoAntigo = getRowCount();
			cursos.addAll(novosCursos);
			fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
		}

		public void limpar() {
			cursos.clear();
			fireTableDataChanged();
		}

		public boolean isEmpty() {
			return cursos.isEmpty();
		}

	}

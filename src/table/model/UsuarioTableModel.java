package table.model;

import javax.swing.table.AbstractTableModel;

import model.Aluno;
import model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -3586211638575736174L;

	private List<Usuario> usuarios;
	private String[] colunas = new String[] { "ID", "Login", "Perfil" };

	public UsuarioTableModel(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public UsuarioTableModel() {
		this.usuarios = new ArrayList<Usuario>();
	}

	public int getRowCount() {
		return usuarios.size();
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

	public void setValueAt(Usuario aValue, int rowIndex) {
		Usuario usuario = usuarios.get(rowIndex);

		usuario.setId(aValue.getId());
		usuario.setLogin(aValue.getLogin());
		usuario.setPerfil(aValue.getPerfil());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
		fireTableCellUpdated(rowIndex, 2);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Usuario usuario = usuarios.get(rowIndex);

		switch (columnIndex) {
		case 0:
			usuario.setId(Integer.parseInt(aValue.toString()));
		case 1:
			usuario.setLogin(aValue.toString());
		case 2:
			usuario.setPerfil(aValue.toString());

		default:
			System.err.println("Índice da coluna inválido");
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Usuario usuarioSelecionado = usuarios.get(rowIndex);
		String valueObject = null;
		switch (columnIndex) {
		case 0:
			valueObject = usuarioSelecionado.getId().toString();
			break;
		case 1:
			valueObject = usuarioSelecionado.getLogin();
			break;
		case 2:
			valueObject = usuarioSelecionado.getPerfil();
			break;
		default:
			System.err.println("Índice inválido para propriedade do bean Usuario.class");
		}

		return valueObject;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Usuario getUsuario(int indiceLinha) {
		return usuarios.get(indiceLinha);
	}

	public void addUsuario(Usuario u) {
		usuarios.add(u);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void removeUsuario(int indiceLinha) {
		usuarios.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void addListaDeUsuarios(List<Usuario> novosUsuarios) {

		int tamanhoAntigo = getRowCount();
		usuarios.addAll(novosUsuarios);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}
	
	//Adiciona a lista de usuários a partir do valor buscado.
	public void addListaDeUsuarios(List<Usuario> novosUsuarios, String dadosDoUsuario, String valorBusca, int indiceLinhaTable) {

		int tamanhoAntigo = getRowCount();		
			
		if (dadosDoUsuario.toLowerCase().contains(valorBusca.toLowerCase())) {
				usuarios.add(novosUsuarios.get(indiceLinhaTable));
				fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
		}		
	}

	public void limpar() {
		usuarios.clear();
		fireTableDataChanged();
	}

	public boolean isEmpty() {
		return usuarios.isEmpty();
	}

}
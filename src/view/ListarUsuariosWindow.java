package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lib.ManipularArquivo;
import model.Usuario;
import table.model.UsuarioTableModel;

public class ListarUsuariosWindow extends AbstractGridWindow {
	private static final long serialVersionUID = 5436871882222628866L;
	
	ManipularArquivo aM = new ManipularArquivo();

	private JButton botaoExcluir;
	private JButton botaoEditar;
	private Usuario usuarioLogado;
	private String idSelecionado;
	
	private JTable jTableUsuarios;
	private UsuarioTableModel model;
	private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	private JDesktopPane desktop;
	
	public ListarUsuariosWindow(JDesktopPane desktop, Usuario usuarioLogado) {
		super("Lista de Usuários");
		
		this.desktop = desktop;
		this.usuarioLogado = usuarioLogado;

		criarComponentes();
		carregarGrid();
	}
	
	private void criarComponentes() {
		//Botão de ação Editar
		botaoEditar = new JButton("Editar");
		botaoEditar.setBounds(15, 30, 100, 25);
		botaoEditar.setEnabled(false);
		add(botaoEditar);
		botaoEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = aM.pegarUsuarioPorId(Integer.parseInt(idSelecionado));
				
				if (usuario instanceof Usuario) {
					CadastrarUsuarioWindow frame = new CadastrarUsuarioWindow(usuario);
					abrirFrame(frame);
					//TODO: Implementar um Observer para atualizar a lista após a edição
				}
			}
		});
		
		//Botão de ação Excluir
		botaoExcluir = new JButton("Excluir");
		botaoExcluir.setBounds(135, 30, 100, 25);
		botaoExcluir.setEnabled(false);
		add(botaoExcluir);
		botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = aM.pegarUsuarioPorId(Integer.parseInt(idSelecionado));

				if (usuario instanceof Usuario) {
					//Remove dado do arquivo
					aM.removerDado(usuario);
					
					//Percorre a lista de usuarios e remove o Usuario com ID selecionado
					listaUsuarios = listaUsuarios.stream()
							.filter(it -> !it.getId().equals(usuario.getId()))
							.collect(Collectors.toList());
					
					//Reseta a lista e atualiza JTable novamente
					model.limpar();
					model.addListaDeUsuarios(listaUsuarios);
					
					//Limpa seleção
					jTableUsuarios.getSelectionModel().clearSelection();
					
					//Desabilita botão de ações (uma vez que a linha selecionada anteriormente não existe, desabilita botões de ação
					desabilitarBotoesDeAcoes();
				}
			}
		});
	}
	
	private void abrirFrame(AbstractWindowFrame frame) {
	    desktop.add(frame);
	    
	    frame.showFrame();
	}

	private void carregarGrid() {
		model = new UsuarioTableModel();
		jTableUsuarios = new JTable(model);

		//Habilita a seleção por linha
		jTableUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Ação Seleção de uma linha
		jTableUsuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();
				
				if (jTableUsuarios.getSelectedRow() != -1) {
					idSelecionado = jTableUsuarios.getValueAt(jTableUsuarios.getSelectedRow(), 0).toString();
				}
			}
		});
		
		//Double Click na linha
		jTableUsuarios.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					//TODO: Abrir tela para visualização do cadastro
				}
			}
		});
		
		try {
			listaUsuarios = aM.pegarUsuarios();
			model.addListaDeUsuarios(listaUsuarios);
		} catch (Exception e) {
			System.err.printf("Erro ao iniciar lista de usuarios: %s.\n", e.getMessage());
		}

		grid = new JScrollPane(jTableUsuarios);
		setLayout(null);
		redimensionarGrid(grid);
		grid.setVisible(true);

		add(grid);
	}
	
	private void habilitarBotoesDeAcoes() {
		//Somente usuário administrador pode manipular dados
		if (Usuario.ADMINISTRADOR.equals(this.usuarioLogado.getPerfil())) {
			botaoEditar.setEnabled(true);
			botaoExcluir.setEnabled(true);
		}
	}
	
	private void desabilitarBotoesDeAcoes() {
		botaoEditar.setEnabled(false);
		botaoExcluir.setEnabled(false);
	}
	
	protected void windowFoiRedimensionada() {
		if (grid != null) {
			redimensionarGrid(grid);
		}
	}
}
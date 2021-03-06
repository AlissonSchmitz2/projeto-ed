package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Usuario;
import br.com.sistemaescolar.observer.ObserverUsuario;
import br.com.sistemaescolar.table.model.UsuarioTableModel;

public class ListarUsuariosWindow extends AbstractGridWindow implements ObserverUsuario {
	private static final long serialVersionUID = 5436871882222628866L;

	ManipularArquivo aM = new ManipularArquivo();

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				buscarUsuario();
			}
		}
	};

	private JButton botaoExcluir;
	private JButton botaoEditar;
	private Usuario usuarioLogado;
	private String idSelecionado;

	// Componentes Para Busca
	private JTextField txfBuscar;
	private JButton btnBuscar;
	private JButton btnLimparBusca;
	private JLabel labelInformacao;

	private JTable jTableUsuarios;
	private UsuarioTableModel model;
	private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	private JDesktopPane desktop;
	private int contAdm = 0;

	public ListarUsuariosWindow(JDesktopPane desktop, Usuario usuarioLogado) {
		super("Lista de Usu�rios");

		this.desktop = desktop;
		this.usuarioLogado = usuarioLogado;

		criarComponentes();
		carregarGrid();
	}

	private void criarComponentes() {
		// Bot�o de a��o Editar
		botaoEditar = new JButton("Editar");
		botaoEditar.setBounds(15, 30, 100, 25);
		botaoEditar.setEnabled(false);
		add(botaoEditar);
		botaoEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = aM.pegarUsuarioPorId(Integer.parseInt(idSelecionado));
				
				contAdm = 0;
				verificarUltimoAdm();
				
				if("Administrador".equals(usuario.getPerfil()) && contAdm == 1) {
					usuario.setUltimoAdm(true);
				}

				if (usuario instanceof Usuario) {
					abrirEdicaoUsuario(usuario);
				}
			}
		});

		// Bot�o de a��o Excluir
		botaoExcluir = new JButton("Excluir");
		botaoExcluir.setBounds(135, 30, 100, 25);
		botaoExcluir.setEnabled(false);
		add(botaoExcluir);
		botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Integer.parseInt(idSelecionado) == usuarioLogado.getId()) {
					JOptionPane.showMessageDialog(null, "Voc� n�o pode excluir seu pr�prio usu�rio!");
					return;
				}
				
				Usuario usuario = aM.pegarUsuarioPorId(Integer.parseInt(idSelecionado));
				
				contAdm = 0;
				verificarUltimoAdm();
				
				//Caso o usu�rio logado mude o perfil para convidado e tente excluir o �ltimo administrador.
				if("Administrador".equals(usuario.getPerfil()) && contAdm == 1) {
					JOptionPane.showMessageDialog(rootPane, "O �ltimo usu�rio administrador n�o pode ser exclu�do!", "",
					JOptionPane.ERROR_MESSAGE, null);
					return;
				}

				if (usuario instanceof Usuario) {
					// Remove dado do arquivo
					aM.removerDado(usuario);

					// Percorre a lista de usuarios e remove o Usuario com ID selecionado
					listaUsuarios = listaUsuarios.stream().filter(it -> !it.getId().equals(usuario.getId()))
							.collect(Collectors.toList());

					// Reseta a lista e atualiza JTable novamente
					model.limpar();
					model.addListaDeUsuarios(listaUsuarios);

					// Limpa sele��o
					jTableUsuarios.getSelectionModel().clearSelection();

					// Desabilita bot�o de a��es (uma vez que a linha selecionada anteriormente n�o
					// existe, desabilita bot�es de a��o
					desabilitarBotoesDeAcoes();
				}
			}
		});

		// Componentes Para Busca
		labelInformacao = new JLabel("Busca:");
		labelInformacao.setBounds(280, 30, 100, 25);
		getContentPane().add(labelInformacao);

		txfBuscar = new JTextField();
		txfBuscar.setBounds(330, 30, 200, 25);
		getContentPane().add(txfBuscar);
		txfBuscar.addKeyListener(acao);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(550, 30, 100, 25);
		getContentPane().add(btnBuscar);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});
		btnBuscar.addKeyListener(acao);

		btnLimparBusca = new JButton("Limpar Busca");
		btnLimparBusca.setBounds(670, 30, 140, 25);
		getContentPane().add(btnLimparBusca);
		btnLimparBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Limpa o campo de busca e mostra a lista inteira novamente.
				txfBuscar.setText("");
				model.limpar();
				try {
					listaUsuarios = aM.pegarUsuarios();
					model.addListaDeUsuarios(listaUsuarios);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de usuarios: %s.\n", e2.getMessage());
				}
			}
		});

		btnLimparBusca.addKeyListener(new KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				// Limpa o campo de busca e mostra a lista inteira novamente.
				txfBuscar.setText("");
				model.limpar();
				try {
					listaUsuarios = aM.pegarUsuarios();
					model.addListaDeUsuarios(listaUsuarios);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de usuarios: %s.\n", e2.getMessage());
				}
			}
		});
	}

	public void buscarUsuario() {
		// Limpa a lista.
		model.limpar();

		// Lista oque estiver relacionado com a busca.
		listaUsuarios = aM.pegarUsuarios(txfBuscar.getText());
		model.addListaDeUsuarios(listaUsuarios);
	}

	private void abrirFrame(AbstractWindowFrame frame) {
		desktop.add(frame);

		frame.showFrame();
	}

	private void carregarGrid() {
		model = new UsuarioTableModel();
		jTableUsuarios = new JTable(model);

		// Habilita a sele��o por linha
		jTableUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// A��o Sele��o de uma linha
		jTableUsuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();

				if (jTableUsuarios.getSelectedRow() != -1) {
					idSelecionado = jTableUsuarios.getValueAt(jTableUsuarios.getSelectedRow(), 0).toString();
				}
			}
		});

		// Double Click na linha
		jTableUsuarios.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (usuarioLogado.possuiPerfilAdministrador()) {
						Usuario usuario = aM.pegarUsuarioPorId(Integer.parseInt(idSelecionado));
						
						contAdm = 0;
						verificarUltimoAdm();
						
						if("Administrador".equals(usuario.getPerfil()) && contAdm == 1) {
							usuario.setUltimoAdm(true);
						}

						if (usuario instanceof Usuario) {
							abrirEdicaoUsuario(usuario);
						}
					}
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

	private void abrirEdicaoUsuario(Usuario usuario) {
		CadastrarUsuariosWindow frame = new CadastrarUsuariosWindow(usuario);
		frame.addObserver(this);
		abrirFrame(frame);
	}

	private void habilitarBotoesDeAcoes() {
		// Somente usu�rio administrador pode manipular dados
		if (usuarioLogado.possuiPerfilAdministrador()) {
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

	@Override
	public void update(Usuario usuario) {
		model.limpar();
		listaUsuarios = aM.pegarUsuarios(txfBuscar.getText());
		model.addListaDeUsuarios(listaUsuarios);
	}
	
	private void verificarUltimoAdm() {
		for(int i = 0; i < listaUsuarios.size(); i++) {
			if("Administrador".equals(listaUsuarios.get(i).getPerfil())) {
				contAdm++;
			}
		}
	}
}
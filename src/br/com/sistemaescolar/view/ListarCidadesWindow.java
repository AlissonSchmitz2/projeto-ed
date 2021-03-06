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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Cidade;
import br.com.sistemaescolar.model.Usuario;
import br.com.sistemaescolar.observer.ObserverCidade;
import br.com.sistemaescolar.table.model.CidadeTableModel;

public class ListarCidadesWindow extends AbstractGridWindow implements ObserverCidade {
	private static final long serialVersionUID = 5436871882222628866L;

	ManipularArquivo aM = new ManipularArquivo();

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				buscarCidade();
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

	private JTable jTableCidades;
	private CidadeTableModel model;
	private List<Cidade> listaCidades = new ArrayList<Cidade>();
	private JDesktopPane desktop;

	public ListarCidadesWindow(JDesktopPane desktop, Usuario usuarioLogado) {
		super("Lista de Cidades");

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
				Cidade cidade = aM.pegarCidadePorId(Integer.parseInt(idSelecionado));

				if (cidade instanceof Cidade) {
					abrirEdicaoCidade(cidade);
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
				Cidade cidade = aM.pegarCidadePorId(Integer.parseInt(idSelecionado));

				if (cidade instanceof Cidade) {
					// Remove dado do arquivo
					aM.removerDado(cidade);

					// Percorre a lista de cidades e remove o Cidade com ID selecionado
					listaCidades = listaCidades.stream().filter(it -> !it.getId().equals(cidade.getId()))
							.collect(Collectors.toList());

					// Reseta a lista e atualiza JTable novamente
					model.limpar();
					model.addListaDeCidades(listaCidades);

					// Limpa sele��o
					jTableCidades.getSelectionModel().clearSelection();

					// Desabilita bot�o de a��es (uma vez que a linha selecionada anteriormente n�o
					// existe, desabilita bot�es de a��o
					desabilitarBotoesDeAcoes();
				}
			}
		});

		// Componentes para busca.
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
				buscarCidade();
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
					listaCidades = aM.pegarCidades();
					model.addListaDeCidades(listaCidades);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de cidades: %s.\n", e2.getMessage());
				}
			}
		});

		btnLimparBusca.addKeyListener(new KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				// Limpa o campo de busca e mostra a lista inteira novamente.
				txfBuscar.setText("");
				model.limpar();
				try {
					listaCidades = aM.pegarCidades();
					model.addListaDeCidades(listaCidades);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de cidades: %s.\n", e2.getMessage());
				}
			}
		});
	}

	public void buscarCidade() {
		// Limpa a lista.
		model.limpar();
		listaCidades = aM.pegarCidades(txfBuscar.getText());
		model.addListaDeCidades(listaCidades);
	}

	private void abrirFrame(AbstractWindowFrame frame) {
		desktop.add(frame);

		frame.showFrame();
	}

	private void carregarGrid() {
		model = new CidadeTableModel();
		jTableCidades = new JTable(model);

		// Habilita a sele��o por linha
		jTableCidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// A��o Sele��o de uma linha
		jTableCidades.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();

				if (jTableCidades.getSelectedRow() != -1) {
					idSelecionado = jTableCidades.getValueAt(jTableCidades.getSelectedRow(), 0).toString();
				}
			}
		});

		// Double Click na linha
		jTableCidades.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (usuarioLogado.possuiPerfilAdministrador()) {
						Cidade cidade = aM.pegarCidadePorId(Integer.parseInt(idSelecionado));

						if (cidade instanceof Cidade) {
							abrirEdicaoCidade(cidade);
						}
					}
				}
			}
		});

		try {
			listaCidades = aM.pegarCidades();
			model.addListaDeCidades(listaCidades);
		} catch (Exception e) {
			System.err.printf("Erro ao iniciar lista de cidades: %s.\n", e.getMessage());
		}

		grid = new JScrollPane(jTableCidades);
		setLayout(null);
		redimensionarGrid(grid);
		grid.setVisible(true);

		add(grid);
	}

	private void abrirEdicaoCidade(Cidade cidade) {
		CadastrarCidadesWindow frame = new CadastrarCidadesWindow(cidade);
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
	public void update(Cidade cidade) {
		model.limpar();
		listaCidades = aM.pegarCidades(txfBuscar.getText());
		model.addListaDeCidades(listaCidades);
	}
}
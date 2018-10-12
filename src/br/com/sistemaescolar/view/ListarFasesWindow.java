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
import br.com.sistemaescolar.model.Fase;
import br.com.sistemaescolar.model.Professor;
import br.com.sistemaescolar.model.Usuario;
import br.com.sistemaescolar.observer.ObserverFase;
import br.com.sistemaescolar.table.model.FaseTableModel;
import br.com.sistemaescolar.table.model.ProfessorTableModel;

public class ListarFasesWindow extends AbstractGridWindow implements ObserverFase{
	private static final long serialVersionUID = 1605315384041909269L;

	ManipularArquivo aM = new ManipularArquivo();
	
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				buscarFase();
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
	
	private JTable jTableFases;
	private FaseTableModel model;
	private List<Fase> listaFases = new ArrayList<Fase>();
	private JDesktopPane desktop;
	
	public ListarFasesWindow(JDesktopPane desktop, Usuario usuarioLogado) {
		super("Lista de Cursos");

		this.desktop = desktop;
		this.usuarioLogado = usuarioLogado;

		criarComponentes();
		carregarGrid();
	}
	
	private void criarComponentes() {
		// Botão de ação Editar
		botaoEditar = new JButton("Editar");
		botaoEditar.setBounds(15, 30, 100, 25);
		botaoEditar.setEnabled(false);
		add(botaoEditar);
		botaoEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fase fase = aM.pegarFasePorId(Integer.parseInt(idSelecionado));
				
				if (fase instanceof Fase) {
					abrirEdicaoFase(fase);
				}
			}
		});

		// Botão de ação Excluir
		botaoExcluir = new JButton("Excluir");
		botaoExcluir.setBounds(135, 30, 100, 25);
		botaoExcluir.setEnabled(false);
		add(botaoExcluir);
		botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				
				Fase fase = aM.pegarFasePorId(Integer.parseInt(idSelecionado));
				
				if (fase instanceof Fase) {
					// Remove dado do arquivo
					aM.removerDado(fase);

					// Percorre a lista de usuarios e remove o Usuario com ID selecionado
					listaFases = listaFases.stream().filter(it -> !it.getId().equals(fase.getId()))
							.collect(Collectors.toList());

					// Reseta a lista e atualiza JTable novamente
					model.limpar();
					model.addListaDeFases(listaFases);

					// Limpa seleção
					jTableFases.getSelectionModel().clearSelection();

					// Desabilita botão de ações (uma vez que a linha selecionada anteriormente não
					// existe, desabilita botões de ação
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
				buscarFase();
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
					listaFases = aM.pegarFases();
					model.addListaDeFases(listaFases);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de fases: %s.\n", e2.getMessage());
				}
			}
		});

		btnLimparBusca.addKeyListener(new KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				// Limpa o campo de busca e mostra a lista inteira novamente ao teclar ENTER.
				btnLimparBusca.doClick();
			}
		});
	}
	
	public void buscarFase() {
		// Limpa a lista.
		model.limpar();

		// Lista oque estiver relacionado com a busca.
		listaFases = aM.pegarFases(txfBuscar.getText());
		model.addListaDeFases(listaFases);
	}
	
	private void abrirFrame(AbstractWindowFrame frame) {
		desktop.add(frame);

		frame.showFrame();
	}
	
	private void carregarGrid() {
		model = new FaseTableModel();
		jTableFases = new JTable(model);

		// Habilita a seleção por linha
		jTableFases.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Ação Seleção de uma linha
		jTableFases.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();

				if (jTableFases.getSelectedRow() != -1) {
					idSelecionado = jTableFases.getValueAt(jTableFases.getSelectedRow(), 0).toString();
				}
			}
		});

		// Double Click na linha
		jTableFases.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (usuarioLogado.possuiPerfilAdministrador()) {
						Fase fase = aM.pegarFasePorId(Integer.parseInt(idSelecionado));
						
						if (fase instanceof Fase) {
							abrirEdicaoFase(fase);
						}
					}
				}
			}
		});

		try {
			listaFases = aM.pegarFases();
			model.addListaDeFases(listaFases);		
		} catch (Exception e) {
			System.err.printf("Erro ao iniciar lista de fases: %s.\n", e.getMessage());
		}

		grid = new JScrollPane(jTableFases);
		setLayout(null);
		redimensionarGrid(grid);
		grid.setVisible(true);

		add(grid);
	}
	
	private void abrirEdicaoFase(Fase fase) {
		CadastrarFasesWindow frame = new CadastrarFasesWindow(fase);
		frame.addObserver(this);
		abrirFrame(frame);
	}
	
	private void habilitarBotoesDeAcoes() {
		// Somente usuário administrador pode manipular dados
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
	public void update(Fase fase) {
		model.limpar();
		listaFases = aM.pegarFases(txfBuscar.getText());
		model.addListaDeFases(listaFases);
	}
	
}

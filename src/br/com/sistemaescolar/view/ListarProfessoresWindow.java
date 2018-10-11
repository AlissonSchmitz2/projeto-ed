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
import br.com.sistemaescolar.model.Professor;
import br.com.sistemaescolar.model.Usuario;
import br.com.sistemaescolar.observer.ObserverProfessor;
import br.com.sistemaescolar.table.model.ProfessorTableModel;

public class ListarProfessoresWindow extends AbstractGridWindow implements ObserverProfessor {
	private static final long serialVersionUID = 4415901016660073914L;

	ManipularArquivo aM = new ManipularArquivo();
	
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				buscarProfessor();
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
	
	private JTable jTableProfessores;
	private ProfessorTableModel model;
	private List<Professor> listaProfessores = new ArrayList<Professor>();
	private JDesktopPane desktop;
	
	public ListarProfessoresWindow(JDesktopPane desktop, Usuario usuarioLogado) {
		super("Lista de Professores");

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
				Professor professor = aM.pegarProfessorPorId(Integer.parseInt(idSelecionado));
				
				if (professor instanceof Professor) {
					abrirEdicaoProfessor(professor);
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
				
				Professor professor = aM.pegarProfessorPorId(Integer.parseInt(idSelecionado));
				
				if (professor instanceof Professor) {
					// Remove dado do arquivo
					aM.removerDado(professor);

					// Percorre a lista de usuarios e remove o Usuario com ID selecionado
					listaProfessores = listaProfessores.stream().filter(it -> !it.getId().equals(professor.getId()))
							.collect(Collectors.toList());

					// Reseta a lista e atualiza JTable novamente
					model.limpar();
					model.addListaDeProfessores(listaProfessores);

					// Limpa seleção
					jTableProfessores.getSelectionModel().clearSelection();

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
				buscarProfessor();
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
					listaProfessores = aM.pegarProfessores();
					model.addListaDeProfessores(listaProfessores);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de professores: %s.\n", e2.getMessage());
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
	
	public void buscarProfessor() {
		// Limpa a lista.
		model.limpar();

		// Lista oque estiver relacionado com a busca.
		listaProfessores = aM.pegarProfessores(txfBuscar.getText());
		model.addListaDeProfessores(listaProfessores);
	}
	
	private void abrirFrame(AbstractWindowFrame frame) {
		desktop.add(frame);

		frame.showFrame();
	}
	
	private void carregarGrid() {
		model = new ProfessorTableModel();
		jTableProfessores = new JTable(model);

		// Habilita a seleção por linha
		jTableProfessores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Ação Seleção de uma linha
		jTableProfessores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();

				if (jTableProfessores.getSelectedRow() != -1) {
					idSelecionado = jTableProfessores.getValueAt(jTableProfessores.getSelectedRow(), 0).toString();
				}
			}
		});

		// Double Click na linha
		jTableProfessores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (usuarioLogado.possuiPerfilAdministrador()) {
						Professor professor = aM.pegarProfessorPorId(Integer.parseInt(idSelecionado));
						
						if (professor instanceof Professor) {
							abrirEdicaoProfessor(professor);
						}
					}
				}
			}
		});

		try {
			listaProfessores = aM.pegarProfessores();
			model.addListaDeProfessores(listaProfessores);		
		} catch (Exception e) {
			System.err.printf("Erro ao iniciar lista de professores: %s.\n", e.getMessage());
		}

		grid = new JScrollPane(jTableProfessores);
		setLayout(null);
		redimensionarGrid(grid);
		grid.setVisible(true);

		add(grid);
	}
	
	private void abrirEdicaoProfessor(Professor professor) {
		CadastrarProfessoresWindow frame = new CadastrarProfessoresWindow(professor);
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
	public void update(Professor professor) {
		model.limpar();
		listaProfessores = aM.pegarProfessores(txfBuscar.getText());
		model.addListaDeProfessores(listaProfessores);
	}

}

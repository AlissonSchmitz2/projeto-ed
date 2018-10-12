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
import br.com.sistemaescolar.model.Disciplina;
import br.com.sistemaescolar.model.Professor;
import br.com.sistemaescolar.model.Usuario;
import br.com.sistemaescolar.observer.ObserverDisciplina;
import br.com.sistemaescolar.table.model.DisciplinaTableModel;
import br.com.sistemaescolar.table.model.ProfessorTableModel;

public class ListarDisciplinasWindow extends AbstractGridWindow implements ObserverDisciplina{
	private static final long serialVersionUID = -7355210321328497096L;

ManipularArquivo aM = new ManipularArquivo();
	
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				buscarDisciplina();
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
	
	private JTable jTableDisciplinas;
	private DisciplinaTableModel model;
	private List<Disciplina> listaDisciplinas = new ArrayList<Disciplina>();
	private JDesktopPane desktop;
	
	public ListarDisciplinasWindow(JDesktopPane desktop, Usuario usuarioLogado) {
		super("Lista de Disciplinas");

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
				Disciplina disciplina = aM.pegarDisciplinaPorId(Integer.parseInt(idSelecionado));
				
				if (disciplina instanceof Disciplina) {
					abrirEdicaoDisciplina(disciplina);
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
				
				Disciplina disciplina = aM.pegarDisciplinaPorId(Integer.parseInt(idSelecionado));
				
				if (disciplina instanceof Disciplina) {
					// Remove dado do arquivo
					aM.removerDado(disciplina);

					// Percorre a lista de usuarios e remove o Usuario com ID selecionado
					listaDisciplinas = listaDisciplinas.stream().filter(it -> !it.getId().equals(disciplina.getId()))
							.collect(Collectors.toList());

					// Reseta a lista e atualiza JTable novamente
					model.limpar();
					model.addListaDeDisciplinas(listaDisciplinas);

					// Limpa seleção
					jTableDisciplinas.getSelectionModel().clearSelection();

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
				buscarDisciplina();
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
					listaDisciplinas = aM.pegarDisciplinas();
					model.addListaDeDisciplinas(listaDisciplinas);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de disciplinas: %s.\n", e2.getMessage());
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
	
	public void buscarDisciplina() {
		// Limpa a lista.
		model.limpar();

		// Lista oque estiver relacionado com a busca.
		listaDisciplinas = aM.pegarDisciplinas(txfBuscar.getText());
		model.addListaDeDisciplinas(listaDisciplinas);
	}
	
	private void abrirFrame(AbstractWindowFrame frame) {
		desktop.add(frame);

		frame.showFrame();
	}
	
	private void carregarGrid() {
		model = new DisciplinaTableModel();
		jTableDisciplinas = new JTable(model);

		// Habilita a seleção por linha
		jTableDisciplinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Ação Seleção de uma linha
		jTableDisciplinas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();

				if (jTableDisciplinas.getSelectedRow() != -1) {
					idSelecionado = jTableDisciplinas.getValueAt(jTableDisciplinas.getSelectedRow(), 0).toString();
				}
			}
		});

		// Double Click na linha
		jTableDisciplinas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (usuarioLogado.possuiPerfilAdministrador()) {
						Disciplina disciplina = aM.pegarDisciplinaPorId(Integer.parseInt(idSelecionado));
						
						if (disciplina instanceof Disciplina) {
							abrirEdicaoDisciplina(disciplina);
						}
					}
				}
			}
		});

		try {
			listaDisciplinas = aM.pegarDisciplinas();
			model.addListaDeDisciplinas(listaDisciplinas);		
		} catch (Exception e) {
			System.err.printf("Erro ao iniciar lista de disciplinas: %s.\n", e.getMessage());
		}

		grid = new JScrollPane(jTableDisciplinas);
		setLayout(null);
		redimensionarGrid(grid);
		grid.setVisible(true);

		add(grid);
	}
	
	private void abrirEdicaoDisciplina(Disciplina disciplina) {
		CadastrarDisciplinasWindow frame = new CadastrarDisciplinasWindow(disciplina);
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
	public void update(Disciplina disciplina) {
		model.limpar();
		listaDisciplinas = aM.pegarDisciplinas(txfBuscar.getText());
		model.addListaDeDisciplinas(listaDisciplinas);
	}
	
}

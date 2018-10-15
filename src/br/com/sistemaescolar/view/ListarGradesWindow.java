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
import br.com.sistemaescolar.model.Grade;
import br.com.sistemaescolar.model.Usuario;
import br.com.sistemaescolar.observer.ObserverGrade;
import br.com.sistemaescolar.table.model.GradeTableModel;

public class ListarGradesWindow extends AbstractGridWindow implements ObserverGrade {
	
	private static final long serialVersionUID = -1307943904186099454L;

	ManipularArquivo aM = new ManipularArquivo();
	
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				buscarGrade();
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
	
	private JTable jTableGrades;
	private GradeTableModel model;
	private List<Grade> listaGrades = new ArrayList<Grade>();
	private JDesktopPane desktop;
	
	public ListarGradesWindow(JDesktopPane desktop, Usuario usuarioLogado) {
		super("Lista de Grades");

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
				Grade grade = aM.pegarGradePorId(Integer.parseInt(idSelecionado), true);
				
				if (grade instanceof Grade) {
					abrirEdicaoGrade(grade);
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
				Grade grade = aM.pegarGradePorId(Integer.parseInt(idSelecionado), false);
				
				if (grade instanceof Grade) {
					// Remove dado do arquivo
					aM.removerDado(grade);

					// Percorre a lista de grades e remove o Usuario com ID selecionado
					listaGrades = listaGrades.stream().filter(it -> !it.getId().equals(grade.getId()))
							.collect(Collectors.toList());

					// Reseta a lista e atualiza JTable novamente
					model.limpar();
					try {
						listaGrades = aM.pegarGrades(false);
						model.addListaDeGrades(listaGrades);
					} catch (Exception e2) {
						System.err.printf("Erro ao iniciar lista de Grades: %s.\n", e2.getMessage());
					}

					// Limpa seleção
					jTableGrades.getSelectionModel().clearSelection();

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
				buscarGrade();
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
					listaGrades = aM.pegarGrades(false);
					model.addListaDeGrades(listaGrades);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de Grades: %s.\n", e2.getMessage());
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
	
	public void buscarGrade() {
		// Limpa a lista.
		model.limpar();

		// Lista oque estiver relacionado com a busca.
		listaGrades = aM.pegarGrades(txfBuscar.getText(), false);
		model.addListaDeGrades(listaGrades);
	}
	
	private void abrirFrame(AbstractWindowFrame frame) {
		desktop.add(frame);

		frame.showFrame();
	}
	
	private void carregarGrid() {
		model = new GradeTableModel();
		jTableGrades = new JTable(model);

		// Habilita a seleção por linha
		jTableGrades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Ação Seleção de uma linha
		jTableGrades.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();

				if (jTableGrades.getSelectedRow() != -1) {
					idSelecionado = jTableGrades.getValueAt(jTableGrades.getSelectedRow(), 0).toString();
				}
			}
		});

		// Double Click na linha
		jTableGrades.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (usuarioLogado.possuiPerfilAdministrador()) {
						Grade grade = aM.pegarGradePorId(Integer.parseInt(idSelecionado), true);
						
						if (grade instanceof Grade) {
							abrirEdicaoGrade(grade);							
						}
					} else {
						Grade grade = aM.pegarGradePorId(Integer.parseInt(idSelecionado), true);
						
						if (grade instanceof Grade) {
							InformacoesGradeWindow frame = new InformacoesGradeWindow(grade);
							abrirFrame(frame);
						}
					}
				}
			}
		});

		try {
			listaGrades = aM.pegarGrades(false);
			model.addListaDeGrades(listaGrades);		
		} catch (Exception e) {
			System.err.printf("Erro ao iniciar lista de Grades: %s.\n", e.getMessage());
		}

		grid = new JScrollPane(jTableGrades);
		setLayout(null);
		redimensionarGrid(grid);
		grid.setVisible(true);

		add(grid);
	}
	
	private void abrirEdicaoGrade(Grade grade) {
		CadastrarGradeWindow frame = new CadastrarGradeWindow(grade);
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
	public void update(Grade grade) {
		model.limpar();
		listaGrades = aM.pegarGrades(txfBuscar.getText(), false);
		model.addListaDeGrades(listaGrades);
	}
}

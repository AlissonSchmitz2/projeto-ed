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
import br.com.sistemaescolar.model.Curso;
import br.com.sistemaescolar.model.Usuario;
import br.com.sistemaescolar.observer.ObserverCursos;
import br.com.sistemaescolar.table.model.CursosTableModel;

public class ListarCursosWindow extends AbstractGridWindow implements ObserverCursos {

	private static final long serialVersionUID = 3408132341357987684L;

		ManipularArquivo aM = new ManipularArquivo();
		
		KeyAdapter acao = new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarCursos();
				}
			}
			};

		private JButton botaoExcluir;
		private JButton botaoEditar;
		private Usuario usuarioLogado;
		private String idSelecionado;
		
		//Componentes Para Busca
		private JTextField txfBuscar;
		private JButton btnBuscar;
		private JButton btnLimparBusca;
		private JLabel labelInformacao;

		private JTable jTableCursos;
		private CursosTableModel model;
		private List<Curso> listaCursos = new ArrayList<Curso>();
		private JDesktopPane desktop;
		
		public ListarCursosWindow(JDesktopPane desktop, Usuario usuarioLogado) {
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
					Curso cursos = aM.pegarCursoPorId(Integer.parseInt(idSelecionado));
					
					if (cursos instanceof Curso) {
						abrirEdicaoCursos(cursos);
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
					Curso cursos = aM.pegarCursoPorId(Integer.parseInt(idSelecionado));

					if (cursos instanceof Curso) {
						// Remove dado do arquivo
						aM.removerDado(cursos);

						// Percorre a lista de alunos e remove o Aluno com ID selecionado
						listaCursos = listaCursos.stream()
								.filter(it -> !it.getId().equals(cursos.getId()))
								.collect(Collectors.toList());

						// Reseta a lista e atualiza JTable novamente
						model.limpar();
						model.addListaDeCursos(listaCursos);

						// Limpa seleção
						jTableCursos.getSelectionModel().clearSelection();

						// Desabilita botão de ações (uma vez que a linha selecionada anteriormente não
						// existe, desabilita botões de ação
						desabilitarBotoesDeAcoes();
					}
				}
			});
			
			//Componentes Para Busca
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
						buscarCursos();	
				}
			});
			
			btnBuscar.addKeyListener(acao);
			
			btnLimparBusca = new JButton("Limpar Busca");
			btnLimparBusca.setBounds(670, 30, 140, 25);
			getContentPane().add(btnLimparBusca);
			btnLimparBusca.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Limpa o campo de busca e mostra a lista inteira novamente.
					txfBuscar.setText("");
					model.limpar();
					try {
						listaCursos = aM.pegarCursos();
						model.addListaDeCursos(listaCursos);
					} catch (Exception e2) {
						System.err.printf("Erro ao iniciar lista de cursos: %s.\n", e2.getMessage());
					}
				}
			});
			
			//Enter para limpar
			btnLimparBusca.addKeyListener(new KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						//Limpa o campo de busca e mostra a lista inteira novamente.
						txfBuscar.setText("");
						model.limpar();
						try {
							listaCursos = aM.pegarCursos();
							model.addListaDeCursos(listaCursos);
						} catch (Exception e2) {
							System.err.printf("Erro ao iniciar lista de cursos: %s.\n", e2.getMessage());
						}
					}
				}
			});
		}

		public void buscarCursos() {
			//Limpa a lista.
			model.limpar();				
			listaCursos = aM.pegarCurso(txfBuscar.getText());
			model.addListaDeCursos(listaCursos);
		}
		
		private void abrirFrame(AbstractWindowFrame frame) {
			desktop.add(frame);

			frame.showFrame();
		}

		private void carregarGrid() {
			model = new CursosTableModel();
			jTableCursos = new JTable(model);

			// Habilita a seleção por linha
			jTableCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			// Ação Seleção de uma linha
			jTableCursos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					habilitarBotoesDeAcoes();

					if (jTableCursos.getSelectedRow() != -1) {
						idSelecionado = jTableCursos.getValueAt(jTableCursos.getSelectedRow(), 0).toString();
					}
				}
			});
			
			//Double Click na linha
			jTableCursos.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						Curso cursos = aM.pegarCursoPorId(Integer.parseInt(idSelecionado));
						
						if (cursos instanceof Curso) {
							if (usuarioLogado.possuiPerfilConvidado()) {
								InformacoesCursosWindow frame = new InformacoesCursosWindow(cursos);
								abrirFrame(frame);					
							} else {
								abrirEdicaoCursos(cursos);
							}
						}
					}
				}
			});

			try {
				listaCursos = aM.pegarCursos();
				model.addListaDeCursos(listaCursos);
			} catch (Exception e) {
				System.err.printf("Erro ao iniciar lista de cursos: %s.\n", e.getMessage());
			}
			

			grid = new JScrollPane(jTableCursos);
			setLayout(null);
			redimensionarGrid(grid);
			grid.setVisible(true);

			add(grid);
		}
		
		private void abrirEdicaoCursos(Curso curso) {
			CadastrarCursosWindow frame = new CadastrarCursosWindow(curso);
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
		public void update(Curso cursos) {
			model.limpar();
			listaCursos = aM.pegarCurso(txfBuscar.getText());
			model.addListaDeCursos(listaCursos);
		}
	}
	

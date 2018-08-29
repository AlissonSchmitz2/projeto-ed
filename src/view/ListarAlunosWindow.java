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
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lib.ManipularArquivo;
import model.Aluno;
import model.Usuario;
import observer.Observer;
import table.model.AlunoTableModel;

public class ListarAlunosWindow extends AbstractGridWindow implements Observer{
	private static final long serialVersionUID = 5436871882222628866L;
	
	ManipularArquivo aM = new ManipularArquivo();
	

	private JButton botaoExcluir;
	private JButton botaoEditar;
	private Usuario usuarioLogado;
	private String idSelecionado;
	
	//Componentes Para Busca
	private JTextField txfBuscar;
	private JButton btnBuscar;
	private JButton btnLimparBusca;
	private JLabel labelInformacao;

	private JTable jTableAlunos;
	private AlunoTableModel model;
	private List<Aluno> listaAlunos = new ArrayList<Aluno>();
	private JDesktopPane desktop;
	
	public ListarAlunosWindow(JDesktopPane desktop, Usuario usuarioLogado) {
		super("Lista de Alunos");

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
				Aluno aluno = aM.pegarAlunoPorId(Integer.parseInt(idSelecionado));
				
				if (aluno instanceof Aluno) {
					abrirEdicaoAluno(aluno);
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
				Aluno aluno = aM.pegarAlunoPorId(Integer.parseInt(idSelecionado));

				if (aluno instanceof Aluno) {
					// Remove dado do arquivo
					aM.removerDado(aluno);

					// Percorre a lista de alunos e remove o Aluno com ID selecionado
					listaAlunos = listaAlunos.stream()
							.filter(it -> !it.getId().equals(aluno.getId()))
							.collect(Collectors.toList());

					// Reseta a lista e atualiza JTable novamente
					model.limpar();
					model.addListaDeAlunos(listaAlunos);

					// Limpa seleção
					jTableAlunos.getSelectionModel().clearSelection();

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
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(550, 30, 100, 25);
		getContentPane().add(btnBuscar);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				//Limpa a lista.
				model.limpar();				
				listaAlunos = aM.pegarAlunos(txfBuscar.getText());
				model.addListaDeAlunos(listaAlunos);				
			}
		});
		
		btnLimparBusca = new JButton("Limpar Busca");
		btnLimparBusca.setBounds(670, 30, 140, 25);
		getContentPane().add(btnLimparBusca);
		btnLimparBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Limpa o campo de busca e mostra a lista inteira novamente.
				txfBuscar.setText("");
				model.limpar();
				try {
					listaAlunos = aM.pegarAlunos();
					model.addListaDeAlunos(listaAlunos);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de alunos: %s.\n", e2.getMessage());
				}
			}
		});
	}

	private void abrirFrame(AbstractWindowFrame frame) {
		desktop.add(frame);

		frame.showFrame();
	}

	private void carregarGrid() {
		model = new AlunoTableModel();
		jTableAlunos = new JTable(model);

		// Habilita a seleção por linha
		jTableAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Ação Seleção de uma linha
		jTableAlunos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();

				if (jTableAlunos.getSelectedRow() != -1) {
					idSelecionado = jTableAlunos.getValueAt(jTableAlunos.getSelectedRow(), 0).toString();
				}
			}
		});
		
		//Double Click na linha
		jTableAlunos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Aluno aluno = aM.pegarAlunoPorId(Integer.parseInt(idSelecionado));
					
					if (aluno instanceof Aluno) {
						if (usuarioLogado.possuiPerfilConvidado()) {
							InformacoesAlunosWindow frame = new InformacoesAlunosWindow(aluno);
							abrirFrame(frame);					
						} else {
							abrirEdicaoAluno(aluno);
						}
					}
				}
			}
		});

		try {
			listaAlunos = aM.pegarAlunos();
			model.addListaDeAlunos(listaAlunos);
		} catch (Exception e) {
			System.err.printf("Erro ao iniciar lista de alunos: %s.\n", e.getMessage());
		}

		grid = new JScrollPane(jTableAlunos);
		setLayout(null);
		redimensionarGrid(grid);
		grid.setVisible(true);

		add(grid);
	}
	
	private void abrirEdicaoAluno(Aluno aluno) {
		CadastrarAlunosWindow frame = new CadastrarAlunosWindow(aluno);
		frame.addObserver(this);
		abrirFrame(frame);
	}
	
	private void habilitarBotoesDeAcoes() {
		// Somente usuário administrador pode manipular dados
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

	@Override
	public void update(Aluno aluno) {
		aM.editarDado(aluno);
		
		//txfBuscar.getText()
		model.limpar();
		listaAlunos = aM.pegarAlunos();
		model.addListaDeAlunos(listaAlunos);
	}
	
}
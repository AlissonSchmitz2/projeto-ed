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
import model.Cidade;
import model.Usuario;
import table.model.CidadeTableModel;

public class ListarCidadesWindow extends AbstractGridWindow {
	private static final long serialVersionUID = 5436871882222628866L;
	
	ManipularArquivo aM = new ManipularArquivo();

	private JButton botaoExcluir;
	private JButton botaoEditar;
	private Usuario usuarioLogado;
	private String idSelecionado;
	
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
		//Bot�o de a��o Editar
		botaoEditar = new JButton("Editar");
		botaoEditar.setBounds(15, 30, 100, 25);
		botaoEditar.setEnabled(false);
		add(botaoEditar);
		botaoEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cidade cidade = aM.pegarCidadePorId(Integer.parseInt(idSelecionado));
				
				if (cidade instanceof Cidade) {
					CadastrarCidadeWindow frame = new CadastrarCidadeWindow(cidade);
					abrirFrame(frame);
					//TODO: Implementar um Observer para atualizar a lista ap�s a edi��o
				}
			}
		});
		
		//Bot�o de a��o Excluir
		botaoExcluir = new JButton("Excluir");
		botaoExcluir.setBounds(135, 30, 100, 25);
		botaoExcluir.setEnabled(false);
		add(botaoExcluir);
		botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cidade cidade = aM.pegarCidadePorId(Integer.parseInt(idSelecionado));
				
				if (cidade instanceof Cidade) {
					//Remove dado do arquivo
					aM.removerDado(cidade);
					
					//Percorre a lista de cidades e remove o Cidade com ID selecionado
					listaCidades = listaCidades.stream()
							.filter(it -> !it.getId().equals(cidade.getId()))
							.collect(Collectors.toList());
					
					//Reseta a lista e atualiza JTable novamente
					model.limpar();
					model.addListaDeCidades(listaCidades);
					
					//Limpa sele��o
					jTableCidades.getSelectionModel().clearSelection();
					
					//Desabilita bot�o de a��es (uma vez que a linha selecionada anteriormente n�o existe, desabilita bot�es de a��o
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
		model = new CidadeTableModel();
		jTableCidades = new JTable(model);

		//Habilita a sele��o por linha
		jTableCidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//A��o Sele��o de uma linha
		jTableCidades.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();
				
				if (jTableCidades.getSelectedRow() != -1) {
					idSelecionado = jTableCidades.getValueAt(jTableCidades.getSelectedRow(), 0).toString();
				}
			}
		});
		
		//Double Click na linha
		jTableCidades.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					//TODO: Abrir tela para visualiza��o do cadastro
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
	
	private void habilitarBotoesDeAcoes() {
		//Somente usu�rio administrador pode manipular dados
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
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lib.ManipularArquivo;

public class ListarAlunosConvidadoWindow  extends WindowFrame {

	private static final long serialVersionUID = 1L;

	public ListarAlunosConvidadoWindow() {
		super("Listagem dos Alunos");
		criarComponentesTelaListarAlunos();
	}
	
	    private JList<String> listaAlunos;
		private ManipularArquivo aM = new ManipularArquivo();
		private ArrayList<String> id_Nome_Alunos = new ArrayList<>();
		
		//Componentes Tela informacoesAluno (tela que abre ao clicar em uma linha da JList).
		private JTextField txfNome;
		private JTextField txfEmail;
		private JTextField txfEndereco;
		private JTextField txfCelular;
		private JLabel labelInformacao;		
		
		//Seta as informações de acordo com a linha selecionada na JList.
		public void setInformacoesAoClicar(JDialog informacoesAluno, String linhaSelecionada) {
			
			ArrayList<String> informacoes = new ArrayList<String>();
			String idAluno = linhaSelecionada.substring(0, 1);
			int indicesInformacoes[] = {2,7,13,14,5};
			informacoes = aM.recuperarDados(indicesInformacoes, "alunos", idAluno);

			txfNome.setText(informacoes.get(0));
			txfEmail.setText(informacoes.get(1));
			txfEndereco.setText(informacoes.get(2) + " (" + informacoes.get(3) + ")");
			txfCelular.setText(informacoes.get(4));
			
			informacoesAluno.setVisible(true);
		}
		
		public void componentesTelaInformacoesAluno(JDialog informacoesAluno) {
			labelInformacao = new JLabel("Nome:");
			labelInformacao.setBounds(10, 20, 50, 25);
			informacoesAluno.getContentPane().add(labelInformacao);
			
			txfNome = new JTextField();
			txfNome.setBackground(Color.WHITE);
			txfNome.setEditable(false);
			txfNome.setBounds(70, 20, 200, 25);
			informacoesAluno.getContentPane().add(txfNome);
			
			labelInformacao = new JLabel("Email:");
			labelInformacao.setBounds(10, 60, 50, 25);
			informacoesAluno.getContentPane().add(labelInformacao);
			
			txfEmail = new JTextField();
			txfEmail.setBackground(Color.WHITE);
			txfEmail.setEditable(false);
			txfEmail.setBounds(70, 60, 200, 25);
			informacoesAluno.getContentPane().add(txfEmail);
			
			labelInformacao = new JLabel("Endereço:");
			labelInformacao.setBounds(10, 100, 100, 25);
			informacoesAluno.getContentPane().add(labelInformacao);
			
			txfEndereco = new JTextField();
			txfEndereco.setBackground(Color.WHITE);
			txfEndereco.setEditable(false);
			txfEndereco.setBounds(70, 100, 200, 25);
			informacoesAluno.getContentPane().add(txfEndereco);
			
			labelInformacao = new JLabel("Celular:");
			labelInformacao.setBounds(10, 140, 50, 25);
			informacoesAluno.getContentPane().add(labelInformacao);
			
			txfCelular = new JTextField();
			txfCelular.setBackground(Color.WHITE);
			txfCelular.setEditable(false);
			txfCelular.setBounds(70, 140, 200, 25);
			informacoesAluno.getContentPane().add(txfCelular);
		}
		
		public void criarComponentesTelaListarAlunos() {
			JDialog informacoesAluno = new JDialog();
			informacoesAluno.setTitle("Perfil do Aluno");
			informacoesAluno.setSize(290,230);
			informacoesAluno.setResizable(false);
			informacoesAluno.setLayout(null);
			informacoesAluno.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			componentesTelaInformacoesAluno(informacoesAluno);
			
			DefaultListModel<String> model = new DefaultListModel<String>();
			listaAlunos = new JList<String>(model);
			
			//Necessário para adicionar o Scroll a JList.
			JScrollPane scroll = new JScrollPane(listaAlunos);
			
			//Faz com que a JList ocupe o tamanho total da intertalFrame.
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			scroll.setBounds(new Rectangle(0, 0, screenSize.width, screenSize.height));
			
			//Recupera os nomes e ids dos alunos cadastrados.
			int indices[] = {0,2};		
			id_Nome_Alunos = aM.recuperarDados(indices, "alunos", "-1");
			
			if (id_Nome_Alunos.isEmpty()) {
				JOptionPane.showMessageDialog(null,"Não há alunos cadastrados no sistema");
			} else {
				//Adiciona os nomes e ids à lista.
				int j = 1;
				int x = 0;
				for (int i = 0; i < id_Nome_Alunos.size(); i += 2) {
					model.add(x, id_Nome_Alunos.get(i) + " - " + id_Nome_Alunos.get(j));
					j += 2;
					x++;
				}
			}							
			
			listaAlunos.addListSelectionListener(new ListSelectionListener() {				
				@Override
				public void valueChanged(ListSelectionEvent e) {				
					setInformacoesAoClicar(informacoesAluno, listaAlunos.getSelectedValue().toString());
				}
			});
			
			//Adiciona o scroll à lista.
			getContentPane().add(scroll, BorderLayout.CENTER);		
		}
}

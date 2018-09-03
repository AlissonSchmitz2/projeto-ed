package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sun.glass.events.KeyEvent;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Usuario;

public class CadastrarPrimeiroUser extends JDialog {

	private static final long serialVersionUID = -111508881355118807L;
	private ManipularArquivo mA = new ManipularArquivo();
	
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastrarPrimeiroUser();
			}
		}
		};
		

	private JTextField txfNome;
	private JPasswordField txfSenha;
	private JButton btnAcessar;
	private JLabel Descricao;
	private String login, senha;

	public CadastrarPrimeiroUser() {
		setSize(250, 200);
		setTitle("Cadastro Administrador");
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		criarComponentes();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void criarComponentes() {
		Descricao = new JLabel("Informe um login para cadastro: ");
		Descricao.setBounds(10, 10, 200, 25);
		getContentPane().add(Descricao);

		txfNome = new JTextField();
		txfNome.setBounds(10, 30, 200, 25);
		getContentPane().add(txfNome);

		Descricao = new JLabel("Informe sua senha: ");
		Descricao.setBounds(10, 65, 200, 25);
		getContentPane().add(Descricao);

		txfSenha = new JPasswordField();
		txfSenha.setBounds(10, 85, 200, 25);
		getContentPane().add(txfSenha);

		btnAcessar = new JButton(new AbstractAction("Cadastrar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				cadastrarPrimeiroUser();
			}
		});
		
		btnAcessar.addKeyListener(acao);
		btnAcessar.setBounds(10, 115, 100, 25);
		getContentPane().add(btnAcessar);

	}
	
	public void cadastrarPrimeiroUser() {
		login = txfNome.getText();
		senha = new String(txfSenha.getPassword());
		Usuario usuario = new Usuario();

		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setPerfil("Administrador");

		try {
			mA.inserirDado(usuario);
			setVisible(false);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

package view;

import java.awt.event.ActionEvent;

import javax.swing.*;
import lib.ManipularArquivo;
import model.Usuario;

public class LoginWindow extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private ManipularArquivo aM = new ManipularArquivo();
	private JTextField txfNome;
	private JPasswordField txfSenha;
	private JButton btnAcessar;
	private JLabel Descricao;
	private String login, senha;

	LoginWindow() {
		setSize(250, 200);
		setTitle("Sistema Escolar");
		setLayout(null);
		setResizable(false);
		criarComponentes();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void criarComponentes() {

		Descricao = new JLabel("Login: ");
		Descricao.setBounds(10, 10, 200, 25);
		getContentPane().add(Descricao);

		txfNome = new JTextField();
		txfNome.setBounds(10, 30, 200, 25);
		txfNome.setToolTipText("Informe seu login");
		getContentPane().add(txfNome);

		Descricao = new JLabel("Senha: ");
		Descricao.setBounds(10, 65, 200, 25);
		getContentPane().add(Descricao);

		txfSenha = new JPasswordField();
		txfSenha.setBounds(10, 85, 200, 25);
		txfSenha.setToolTipText("Informe sua senha");
		getContentPane().add(txfSenha);

		btnAcessar = new JButton(new AbstractAction("Acessar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				login = txfNome.getText();
				senha = new String(txfSenha.getPassword());
				
				try {
					Usuario usuarioLogado = aM.pegarUsuarioPorLoginSenha(login, senha);
					if (usuarioLogado instanceof Usuario) {
						setVisible(false);
						Window tela = new Window(usuarioLogado);
						tela.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Login e/ou senha incorretos!");
					}
				} catch (Exception message) {
					//Cadastra administrador caso Exception seja lançada
					new CadastrarPrimeiroUser().setVisible(true);
				}
			}
		});
		btnAcessar.setBounds(10, 115, 100, 25);

		getContentPane().add(btnAcessar);

	}

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new LoginWindow().setVisible(true);
        		
        		//Modo debug. Pula a tela de login ;)
        		//new Window(new Usuario(1, "Teste", "teste", Usuario.ADMINISTRADOR)).setVisible(true);
            }
        });
	}
}

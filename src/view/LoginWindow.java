package view;

import java.awt.event.ActionEvent;
import java.io.File;

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
		componentesCriar();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void componentesCriar() {

		Descricao = new JLabel("Informe seu login: ");
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

		btnAcessar = new JButton(new AbstractAction("Acessar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				login = txfNome.getText();
				senha = new String(txfSenha.getPassword());
				
				try {
					Usuario usuarioLogado = aM.getUsuarioByCodigoSenha(login, senha);
					if (usuarioLogado instanceof Usuario) {
						setVisible(false);
						
						Window tela = new Window(usuarioLogado);
						tela.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Id ou senha incorreta!");
					}
				} catch (Exception message) {
					//Se uma exceção for retornada, nenhum usuário foi encontrado. Diante disso, abre a tela para cadastro do usuário
					//TODO: fechar tela login, abrir window já com a tela de cadastro de usuário aberta
					//TODO: ou talvez criar uma JDialog simple somente com os campos usuário e senha (Essa parece ser a mais fácil)
					System.out.println("Abrir a tela para cadastro do novo usuário");
				}
			}
		});
		btnAcessar.setBounds(10, 115, 100, 25);

		getContentPane().add(btnAcessar);

	}

	public static void main(String[] args) {
		new LoginWindow().setVisible(true);
	}
}

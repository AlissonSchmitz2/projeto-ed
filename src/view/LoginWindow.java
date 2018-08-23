package view;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;
import lib.ManipularArquivo;

public class LoginWindow extends JDialog {
	private static final long serialVersionUID = 1L;
	
	ManipularArquivo aM = new ManipularArquivo();
	private JTextField txfNome;
	private JPasswordField txfSenha;
	private JButton btnAcessar;
	private JLabel Descricao;
	String login, senha;

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
				
				String destino = new File(".\\src\\data\\usuarios.txt").getAbsolutePath();

				if (login.equals("admin") && senha.equals("admin")
						&& (aM.lerArquivo(destino, login, senha) == "CadastraAdministrador")) {
					setVisible(false);
					//new Menu().setVisible(true);
				} else if (aM.lerArquivo(destino, login, senha) == "Convidado") {
					setVisible(false);
					//new SalaConvidado().setVisible(true);
				} else if (aM.lerArquivo(destino, login, senha) == "Administrador") {
					setVisible(false);
					//new Menu().setVisible(true);
				} else if (aM.lerArquivo(destino, login, senha) == "Incorreto") {
					JOptionPane.showMessageDialog(null, "Id ou senha incorreta!");
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

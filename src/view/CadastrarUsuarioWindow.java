package view;

import model.Usuario;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import lib.ManipularArquivo;

public class CadastrarUsuarioWindow extends WindowFrame {
	private static final long serialVersionUID = 1L;

	private JPasswordField txfSenha;
	private JTextField txfCodAluno;
	private JComboBox<String> txfPerfil;
	private JButton btnCadastra;
	private JButton btnLimpar;
	private JLabel saida;

	public CadastrarUsuarioWindow() {
		super("Cadastrar Usuário");

		criarComponentes();
	}

	private void criarComponentes() {
		saida = new JLabel("Digite seu codigo para cadastro: ");
		saida.setBounds(15, 10, 200, 25);
		getContentPane().add(saida);

		txfCodAluno = new JTextField();
		txfCodAluno.setBounds(15, 30, 200, 25);
		getContentPane().add(txfCodAluno);

		saida = new JLabel("Digite sua senha:");
		saida.setBounds(15, 60, 200, 25);
		getContentPane().add(saida);

		txfSenha = new JPasswordField();
		txfSenha.setBounds(15, 80, 200, 25);
		getContentPane().add(txfSenha);

		saida = new JLabel("Escolha seu perfil:");
		saida.setBounds(15, 110, 200, 25);
		getContentPane().add(saida);

		txfPerfil = new JComboBox<String>();
		txfPerfil.addItem("-Selecione-");
		txfPerfil.addItem(Usuario.ADMINISTRADOR);
		txfPerfil.addItem(Usuario.CONVIDADO);
		txfPerfil.setBounds(15, 130, 200, 25);
		getContentPane().add(txfPerfil);

		btnLimpar = new JButton(new AbstractAction("Limpar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				limparFormulario();
			}
		});

		btnLimpar.setBounds(15, 170, 95, 25);
		getContentPane().add(btnLimpar);

		btnCadastra = new JButton(new AbstractAction("Cadastrar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				Usuario usuario = new Usuario();

				String codUsuario = txfCodAluno.getText();
				usuario.setLogin(codUsuario);

				String senhaUsuario = new String(txfSenha.getPassword());
				usuario.setSenha(senhaUsuario);

				String perfilUsuario = txfPerfil.getSelectedItem().toString();
				usuario.setPerfil(perfilUsuario);

				try {
					ManipularArquivo aM = new ManipularArquivo();
					aM.inserirDado(usuario);
					limparFormulario();
					JOptionPane.showMessageDialog(null,"Usuario cadastrado com sucesso!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCadastra.setBounds(120, 170, 95, 25);
		getContentPane().add(btnCadastra);
	}

	public void limparFormulario() {
		
		txfCodAluno.setText("");
		txfSenha.setText("");
		txfPerfil.setSelectedIndex(0);
		
	}
}

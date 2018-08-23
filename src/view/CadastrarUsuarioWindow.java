package view;

import model.Usuario;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import lib.ArquivoManipular;

public class CadastrarUsuarioWindow extends WindowFrame {
	private static final long serialVersionUID = 1L;

	private JPasswordField txfSenha;
	private JTextField txfCodAluno;
	private JComboBox<String> txfPerfil;
	private JButton btnCadastra;
	private JButton btnVoltar;
	private JLabel saida;

	public CadastrarUsuarioWindow() {
		super("Cadastrar Usuário");

		criarComponentes();
	}

	private void criarComponentes() {
		saida = new JLabel("Digite seu codigo para cadastro: ");
		saida.setBounds(10, 10, 200, 25);
		getContentPane().add(saida);

		txfCodAluno = new JTextField();
		txfCodAluno.setBounds(10, 30, 200, 25);
		getContentPane().add(txfCodAluno);

		saida = new JLabel("Digite sua senha:");
		saida.setBounds(10, 60, 200, 25);
		getContentPane().add(saida);

		txfSenha = new JPasswordField();
		txfSenha.setBounds(10, 80, 200, 25);
		getContentPane().add(txfSenha);

		saida = new JLabel("Escolha seu perfil:");
		saida.setBounds(10, 110, 200, 25);
		getContentPane().add(saida);

		txfPerfil = new JComboBox<String>();
		txfPerfil.addItem("Selecione");
		txfPerfil.addItem("Administrador");
		txfPerfil.addItem("Convidado");
		txfPerfil.setBounds(10, 135, 200, 25);
		getContentPane().add(txfPerfil);

		btnVoltar = new JButton(new AbstractAction("Voltar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new LoginWindow().setVisible(true);
			}
		});
		
		btnVoltar.setBounds(10, 170, 95, 25);
		getContentPane().add(btnVoltar);

		btnCadastra = new JButton(new AbstractAction("Cadastrar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				Usuario usuario = new Usuario();

				String codUsuario = txfCodAluno.getText();
				usuario.setNomeUsuario(codUsuario);

				String senhaUsuario = new String(txfSenha.getPassword());
				;
				usuario.setSenha(senhaUsuario);

				String perfilUsuario = txfPerfil.getSelectedItem().toString();
				usuario.setPerfil(perfilUsuario);

				try {
					ArquivoManipular aM = new ArquivoManipular();
					aM.inserirDado(usuario);
					//TODO: Limpar o formulário
					//TODO: exibir mensagem de sucesso
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCadastra.setBounds(115, 170, 95, 25);
		getContentPane().add(btnCadastra);
	}
}

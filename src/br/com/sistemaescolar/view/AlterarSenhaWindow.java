package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.sun.glass.events.KeyEvent;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Usuario;

public class AlterarSenhaWindow extends AbstractWindowFrame {
	private static final long serialVersionUID = 1L;

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				alterarSenha();
			}
		}
	};

	private JPasswordField senhaAntiga;
	private JPasswordField novaSenha;
	private JPasswordField confirmaSenha;
	private JButton btnLimpar;
	private JButton btnConfirma;
	private JLabel descricaoCampo;
	private Usuario usuarioLogado;
	private ManipularArquivo aM = new ManipularArquivo();

	public AlterarSenhaWindow(Usuario usuarioLogado) {
		super("Alterar Senha");
		this.usuarioLogado = usuarioLogado;
		criarComponentes();
	}

	public void criarComponentes() {
		descricaoCampo = new JLabel("Senha Atual:");
		descricaoCampo.setBounds(15, 10, 200, 25);
		getContentPane().add(descricaoCampo);

		senhaAntiga = new JPasswordField();
		senhaAntiga.setBounds(15, 30, 200, 25);
		senhaAntiga.setToolTipText("Informe sua senha atual");
		getContentPane().add(senhaAntiga);
		senhaAntiga.addKeyListener(acao);

		descricaoCampo = new JLabel("Nova senha:");
		descricaoCampo.setBounds(15, 60, 200, 25);
		getContentPane().add(descricaoCampo);

		novaSenha = new JPasswordField();
		novaSenha.setBounds(15, 80, 200, 25);
		novaSenha.setToolTipText("Informe sua nova senha");
		getContentPane().add(novaSenha);
		novaSenha.addKeyListener(acao);

		descricaoCampo = new JLabel("Confirme a nova senha:");
		descricaoCampo.setBounds(15, 110, 200, 25);
		getContentPane().add(descricaoCampo);

		confirmaSenha = new JPasswordField();
		confirmaSenha.setBounds(15, 130, 200, 25);
		confirmaSenha.setToolTipText("Confirme a nova senha");
		getContentPane().add(confirmaSenha);
		confirmaSenha.addKeyListener(acao);

		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 170, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar os campos");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				novaSenha.setText("");
				confirmaSenha.setText("");
				senhaAntiga.setText("");
			}
		});

		btnConfirma = new JButton("Confirmar");
		btnConfirma.setBounds(120, 170, 95, 25);
		getContentPane().add(btnConfirma);
		btnConfirma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alterarSenha();
			}
		});
		btnConfirma.addKeyListener(acao);
	}

	public void alterarSenha() {
		if ("".equals(novaSenha.getText()) == false && novaSenha.getText().equals(confirmaSenha.getText())
				&& senhaAntiga.getText().equals(usuarioLogado.getSenha())) {

			// Atualiza a senha do usuário para depois atualiza-lá no TXT.
			usuarioLogado.setSenha(novaSenha.getText());

			// Atualiza a senha no TXT.
			aM.editarDado(usuarioLogado);

			JOptionPane.showMessageDialog(null, "Senha alterada com sucesso");
			btnLimpar.doClick();
		} else {
			JOptionPane.showMessageDialog(null, "Informações incorretas!");
		}
	}
}
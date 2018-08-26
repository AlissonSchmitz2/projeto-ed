package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import lib.ManipularArquivo;
import model.Usuario;

public class AlterarSenhaWindow extends WindowFrame{
	private static final long serialVersionUID = 1L;
	
	private JPasswordField senhaAntiga;
	private JPasswordField novaSenha;
	private JPasswordField confirmaSenha;
	private JButton btnLimpar;
	private JButton btnConfirma;
	private JLabel descricaoCampo;
	private Integer idAtual;
	private String loginAtual;
	private String senhaAtual;
	private Usuario usuarioLogado;
	
	public AlterarSenhaWindow(Usuario usuarioLogado) {		
		super("Alterar Senha");
		this.idAtual = usuarioLogado.getId();
		this.loginAtual = usuarioLogado.getLogin();
		this.senhaAtual = usuarioLogado.getSenha();
		this.usuarioLogado = usuarioLogado;
		criarComponentes();
	}
	
	public void criarComponentes() {
		descricaoCampo = new JLabel("Informe sua senha antiga:");
		descricaoCampo.setBounds(15, 10, 200, 25);
		getContentPane().add(descricaoCampo);
		
		senhaAntiga = new JPasswordField();
		senhaAntiga.setBounds(15, 30, 200, 25);
		getContentPane().add(senhaAntiga);
		
		descricaoCampo = new JLabel("Informe a nova senha:");
		descricaoCampo.setBounds(15, 60, 200, 25);
		getContentPane().add(descricaoCampo);
		
		novaSenha = new JPasswordField();
		novaSenha.setBounds(15, 80, 200, 25);
		getContentPane().add(novaSenha);
		
		descricaoCampo = new JLabel("Confirme a nova senha:");
		descricaoCampo.setBounds(15, 110, 200, 25);
		getContentPane().add(descricaoCampo);
		
		confirmaSenha = new JPasswordField();
		confirmaSenha.setBounds(15, 130, 200, 25);
		getContentPane().add(confirmaSenha);
		
		btnLimpar = new JButton(new AbstractAction("Limpar") {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				senhaAntiga.setText("");
				novaSenha.setText("");
				confirmaSenha.setText("");
			}
		});
		btnLimpar.setBounds(15, 170, 95, 25);
		getContentPane().add(btnLimpar);
		
		btnConfirma = new JButton(new AbstractAction("Confirmar") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (novaSenha.getText().equals(confirmaSenha.getText()) && senhaAntiga.getText().equals(senhaAtual)) {
					ManipularArquivo aM = new ManipularArquivo();
					String textoAntigo = idAtual + "," + loginAtual + "," + senhaAtual;
					String textoNovo = idAtual + "," + loginAtual + "," + novaSenha.getText();
					
					//Realiza a alteração da senha.
					aM.substituirInformacao("usuarios", textoAntigo, textoNovo);
					
					//Atualiza as informações do usuário após a alteração da senha.
					usuarioLogado.setSenha(novaSenha.getText());
					senhaAtual = novaSenha.getText();					
					
					JOptionPane.showMessageDialog(null,"Senha alterada com sucesso");
					btnLimpar.doClick();
				} else {
					JOptionPane.showMessageDialog(null,"Informações incorretas!");
				}
			}
		});
		btnConfirma.setBounds(120, 170, 95, 25);
		getContentPane().add(btnConfirma);
	}
	
}

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import lib.ManipularArquivo;
import model.Usuario;

public class AlterarSenhaWindow extends AbstractWindowFrame {
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
		descricaoCampo = new JLabel("Senha Atual:");
		descricaoCampo.setBounds(15, 10, 200, 25);
		getContentPane().add(descricaoCampo);
		
		senhaAntiga = new JPasswordField();
		senhaAntiga.setBounds(15, 30, 200, 25);
		senhaAntiga.setToolTipText("Informe sua senha atual");
		getContentPane().add(senhaAntiga);
		
		descricaoCampo = new JLabel("Nova senha:");
		descricaoCampo.setBounds(15, 60, 200, 25);
		getContentPane().add(descricaoCampo);
		
		novaSenha = new JPasswordField();
		novaSenha.setBounds(15, 80, 200, 25);
		novaSenha.setToolTipText("Informe sua nova senha");
		getContentPane().add(novaSenha);
		
		descricaoCampo = new JLabel("Confirme a nova senha:");
		descricaoCampo.setBounds(15, 110, 200, 25);
		getContentPane().add(descricaoCampo);
		
		confirmaSenha = new JPasswordField();
		confirmaSenha.setBounds(15, 130, 200, 25);
		confirmaSenha.setToolTipText("Confirme a nova senha");
		getContentPane().add(confirmaSenha);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 170, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar os campos");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				limparFormulario();
			}
		});
		
		btnConfirma = new JButton("Confirmar");
		btnConfirma.setBounds(120, 170, 95, 25);
		getContentPane().add(btnConfirma);
		btnConfirma.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (novaSenha.getText().equals(confirmaSenha.getText()) && senhaAntiga.getText().equals(senhaAtual)) {
					ManipularArquivo aM = new ManipularArquivo();

					//Atualiza as informações do usuário após a alteração da senha.
					usuarioLogado.setSenha(novaSenha.getText()); //Todo: getText parece estar depreciado
					senhaAtual = novaSenha.getText(); //Todo: getText parece estar depreciado
					
					aM.editarDado(usuarioLogado);
					
					limparFormulario();
					JOptionPane.showMessageDialog(null,"Senha alterada com sucesso");
					btnLimpar.doClick();
				} else {
					JOptionPane.showMessageDialog(null,"Informações incorretas!");
				}
			}
		});
		
	}
	
	public void limparFormulario() {
		senhaAntiga.setText("");
		novaSenha.setText("");
		confirmaSenha.setText("");
	}
}
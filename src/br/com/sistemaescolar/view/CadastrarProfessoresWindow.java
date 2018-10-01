package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Disciplina;
import br.com.sistemaescolar.model.Professor;

public class CadastrarProfessoresWindow extends AbstractWindowFrame{

	private static final long serialVersionUID = 4734772377961557461L;
	
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastrarProfessores();
			}
		}
	};
	
	Professor prof = new Professor();
	
	private JLabel labes;
	private JButton btnCadastrar, btnLimpar;
	private JTextField txfProf;
	
	public CadastrarProfessoresWindow() {
		super("Cadastrar Professor");
		criarComponentes();
	}
	
	public void criarComponentes() {
		
		labes = new JLabel("Professor:");
		labes.setBounds(15, 10, 250, 25);
		getContentPane().add(labes);
		
		txfProf = new JTextField();
		txfProf.setBounds(15, 30, 200, 25);
		txfProf.setToolTipText("Informe o professor");
		getContentPane().add(txfProf);
		txfProf.addKeyListener(acao);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 80, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar o campo");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(120, 80, 95, 25);
		getContentPane().add(btnCadastrar);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarProfessores();
			}
		});
		btnCadastrar.addKeyListener(acao);

	}
	
	public void cadastrarProfessores() {
		if(validarCamposObrigatorios()) {
			JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para cadastrar!", "",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		
		prof.setProfessor(txfProf.getText());
		
		ManipularArquivo aM = new ManipularArquivo();
		aM.inserirDado(prof);
		
		JOptionPane.showMessageDialog(null, "Disciplina cadastrada com sucesso!");
		limparFormulario();
	}
	
	public boolean validarCamposObrigatorios() {
		
		if(txfProf.getText().isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	public void limparFormulario() {
		txfProf.setText("");
		prof = new Professor();
	}
	
}

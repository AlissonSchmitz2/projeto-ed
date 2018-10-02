package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Curso;

public class CadastrarCursosWindow extends AbstractWindowFrame {

	private static final long serialVersionUID = -1040641568238182334L;
	
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastrarCurso();
			}
		}
	};
	
	private JLabel labes;
	private JButton btnSalvar, btnLimpar;
	private JTextField txfCurso;
	
	Curso curso = new Curso();
	
	public CadastrarCursosWindow() {
		super("Cadastrar Cursos");
		criarComponentes();
	}

	public void criarComponentes() {
		
		labes = new JLabel("Curso:");
		labes.setBounds(15, 10, 250, 25);
		getContentPane().add(labes);

		txfCurso = new JTextField();
		txfCurso.setBounds(15, 30, 200, 25);
		txfCurso.setToolTipText("Digite o curso");
		getContentPane().add(txfCurso);
		txfCurso.addKeyListener(acao);

		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 80, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar o campo");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limparFormulario();
			}
		});
		
		
		btnSalvar = new JButton(new AbstractAction("Cadastrar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				cadastrarCurso();
			}
		});
		
		btnSalvar.addKeyListener(acao);
		btnSalvar.setBounds(120, 80, 95, 25);
		getContentPane().add(btnSalvar);

	}

	public void cadastrarCurso() {
		if(validarCamposObrigatorios()) {
			JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para cadastrar!", "",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		
		curso.setCurso(txfCurso.getText());
		
		ManipularArquivo aM = new ManipularArquivo();
		aM.inserirDado(curso);
		
		JOptionPane.showMessageDialog(null, "Curso cadastrado com sucesso!");
		limparFormulario();
	}
	
	public boolean validarCamposObrigatorios() {
		
		if(txfCurso.getText().isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	public void limparFormulario() {
		txfCurso.setText("");
		curso = new Curso();
	}

}



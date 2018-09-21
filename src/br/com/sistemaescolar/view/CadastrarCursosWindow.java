package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CadastrarCursosWindow extends AbstractWindowFrame {

	private static final long serialVersionUID = -1040641568238182334L;
	
	private JLabel labes;
	private JButton btnSalvar, btnLimpar;
	private JTextField txfCurso;
	
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
		

		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 80, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar o campo");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(120, 80, 95, 25);
		getContentPane().add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		//btnSalvar.addKeyListener((KeyListener) acao);
		
		
	}

}



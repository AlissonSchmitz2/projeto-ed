package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CadastrarDisciplinasWindow extends AbstractWindowFrame{

	private static final long serialVersionUID = 4734772377961557461L;

	private JLabel labes;
	private JButton btnCadastrar, btnLimpar;
	private JTextField txfDisciplina;
	
	public CadastrarDisciplinasWindow() {
		super("Cadastrar Disciplina");
		criarComponentes();
	}
	
	public void criarComponentes() {
		
		labes = new JLabel("Disciplina:");
		labes.setBounds(15, 10, 250, 25);
		getContentPane().add(labes);
		
		txfDisciplina = new JTextField();
		txfDisciplina.setBounds(15, 30, 200, 25);
		txfDisciplina.setToolTipText("Informe a disciplina");
		getContentPane().add(txfDisciplina);
		
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
				
			}
		});
		

	}
	
}

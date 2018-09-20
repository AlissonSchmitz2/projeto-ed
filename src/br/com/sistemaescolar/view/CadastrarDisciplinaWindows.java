package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class CadastrarDisciplinaWindows extends AbstractWindowFrame{

		private static final long serialVersionUID = -6470064732665196009L;
		
			private JLabel labes;
			private JButton btnSalvar, btnLimpar;
			private JComboBox<String> cbxCurso, cbxFases, cbxDisciplina;
			
			public CadastrarDisciplinaWindows() {
				super("Cadastrar Fase");
				criarComponentes();
			}

			public void criarComponentes() {
				
				labes = new JLabel("Disciplina:");
				labes.setBounds(15, 10, 250, 25);
				getContentPane().add(labes);

				cbxDisciplina = new JComboBox<String>();
				cbxDisciplina.addItem("-Selecione-");
				cbxDisciplina.setBounds(15, 30, 200, 25);
				cbxDisciplina.setToolTipText("Informe a disciplina");
				getContentPane().add(cbxFases);

				
				labes = new JLabel("Fase:");
				labes.setBounds(15, 10, 250, 25);
				getContentPane().add(labes);

				cbxFases = new JComboBox<String>();
				cbxFases.addItem("-Selecione-");
				cbxFases.addItem("1 FASE");
				cbxFases.addItem("2 FASE");
				cbxFases.setBounds(15, 30, 200, 25);
				cbxFases.setToolTipText("Informe a fase");
				getContentPane().add(cbxFases);
				
				labes = new JLabel("Curso:");
				labes.setBounds(15, 60, 250, 25);
				getContentPane().add(labes);

				cbxCurso = new JComboBox<String>();
				cbxCurso.addItem("-Selecione-");
				cbxCurso.setBounds(15, 80, 200, 25);
				cbxCurso.setToolTipText("Informe o curso");
				getContentPane().add(cbxCurso);
				
				btnLimpar = new JButton("Limpar");
				btnLimpar.setBounds(15, 170, 95, 25);
				btnLimpar.setToolTipText("Clique aqui para limpar o campo");
				getContentPane().add(btnLimpar);
				btnLimpar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
					}
				});
				
				btnSalvar = new JButton("Salvar");
				btnSalvar.setBounds(120, 170, 95, 25);
				getContentPane().add(btnSalvar);
				btnSalvar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					}
				});
				

			}

		


	
	
}

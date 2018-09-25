package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CadastrarFasesWindow extends AbstractWindowFrame{

	private static final long serialVersionUID = -6470064732665196009L;
	
		private JLabel labes;
		private JButton btnSalvar, btnLimpar;
		private JComboBox<String> cbxCurso, cbxFases;
		
		public CadastrarFasesWindow() {
			super("Cadastrar Fase");
			criarComponentes();
		}

		public void criarComponentes() {
			
			labes = new JLabel("Fase:");
			labes.setBounds(15, 10, 250, 25);
			getContentPane().add(labes);

			cbxFases = new JComboBox<String>();
			cbxFases.addItem("-Selecione-");
			for(int i=1; i<11; i++) {
				cbxFases.addItem(i + " FASE");
			}
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
			btnLimpar.setBounds(15, 130, 95, 25);
			btnLimpar.setToolTipText("Clique aqui para limpar o campo");
			getContentPane().add(btnLimpar);
			btnLimpar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			
			btnSalvar = new JButton("Salvar");
			btnSalvar.setBounds(120, 130, 95, 25);
			getContentPane().add(btnSalvar);
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			

		}

	}


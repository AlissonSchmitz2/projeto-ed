package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CadastrarProfessorWindow extends AbstractWindowFrame{

	private static final long serialVersionUID = 4734772377961557461L;

	private JLabel labes;
	private JButton btnSalvar, btnLimpar;
	private JComboBox<String> cbxCurso, cbxFases, cbxDisciplina;
	private JTextField txfProf;
	
	public CadastrarProfessorWindow() {
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
		
		labes = new JLabel("Disciplina:");
		labes.setBounds(15, 60, 250, 25);
		getContentPane().add(labes);

		cbxDisciplina = new JComboBox<String>();
		cbxDisciplina.addItem("-Selecione-");
		cbxDisciplina.setBounds(15, 80, 200, 25);
		cbxDisciplina.setToolTipText("Informe a disciplina");
		getContentPane().add(cbxDisciplina);
		
		labes = new JLabel("Fase:");
		labes.setBounds(15, 110, 250, 25);
		getContentPane().add(labes);

		cbxFases = new JComboBox<String>();
		cbxFases.addItem("-Selecione-");
		for(int i=1; i<11; i++) {
			cbxFases.addItem(i + " FASE");
		}
		cbxFases.setBounds(15, 130, 200, 25);
		cbxFases.setToolTipText("Informe a fase");
		getContentPane().add(cbxFases);
		
		labes = new JLabel("Curso:");
		labes.setBounds(15, 160, 250, 25);
		getContentPane().add(labes);

		cbxCurso = new JComboBox<String>();
		cbxCurso.addItem("-Selecione-");
		cbxCurso.setBounds(15, 180, 200, 25);
		cbxCurso.setToolTipText("Informe o curso");
		getContentPane().add(cbxCurso);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 220, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar o campo");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(120, 220, 95, 25);
		getContentPane().add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		

	}
	
}

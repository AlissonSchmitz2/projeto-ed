package br.com.sistemaescolar.view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import br.com.sistemaescolar.model.Curso;

public class InformacoesCursosWindow extends AbstractWindowFrame{

	private static final long serialVersionUID = -2272656927589218395L;

	private JTextField txfCod;
	private JLabel labes;
	
	private Curso cursoSelecionado;

	public InformacoesCursosWindow(Curso cursoSelecionado) {
		super("Informações do Curso");
		this.cursoSelecionado = cursoSelecionado;
		criarComponentes();		
	}
	
	public void criarComponentes() {
		labes = new JLabel("Curso:");
		labes.setBounds(15, 10, 250, 25);
		getContentPane().add(labes);

		txfCod = new JTextField(cursoSelecionado.getCurso());
		txfCod.setBackground(Color.WHITE);
		txfCod.setEditable(false);
		txfCod.setBounds(15, 30, 125, 25);
		getContentPane().add(txfCod);

	}
	
	
}

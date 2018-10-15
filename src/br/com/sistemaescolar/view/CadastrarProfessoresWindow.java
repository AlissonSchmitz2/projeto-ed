package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.sistemaescolar.enums.TitulosDocentes;
import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Professor;
import br.com.sistemaescolar.observer.ObserverProfessor;
import br.com.sistemaescolar.observer.SubjectProfessor;

public class CadastrarProfessoresWindow extends AbstractWindowFrame implements SubjectProfessor{

	private static final long serialVersionUID = 4734772377961557461L;
	
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastrarProfessores();
			}
		}
	};
	
	Professor professor = new Professor();
	
	private JLabel labes;
	private JButton btnCadastrar, btnLimpar;
	private JTextField txfProf;
	private JComboBox<String> cbxTitulo;
	
	private ArrayList<ObserverProfessor> observers = new ArrayList<ObserverProfessor>();
	private ManipularArquivo aM = new ManipularArquivo();
	
	public CadastrarProfessoresWindow() {
		super("Cadastrar Professor");
		criarComponentes();
	}
	
	public CadastrarProfessoresWindow(Professor professor) {
		super("Editar Professor");
		this.professor = professor;
		criarComponentes();
		setarValores(professor);
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
		
		labes = new JLabel("Título Docente:");
		labes.setBounds(15, 60, 250, 25);
		getContentPane().add(labes);
		
		cbxTitulo = new JComboBox<String>();
		
		cbxTitulo.addItem("-Selecione-");
		TitulosDocentes.getTitulosDocentes().forEach((codigo, descricao) -> cbxTitulo.addItem(descricao));
		
		cbxTitulo.setBounds(15, 80, 200, 25);
		cbxTitulo.setToolTipText("Informe o título docente");
		cbxTitulo.addKeyListener(acao);
		getContentPane().add(cbxTitulo);
		
		
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 130, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar o campo");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (professor.getId() != null) {
					setarValores(professor);
				} else {
					limparFormulario();
				}
			}
		});
		
		btnCadastrar = new JButton("Salvar");
		btnCadastrar.setBounds(120, 130, 95, 25);
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
		
		professor.setNome(txfProf.getText());
		professor.setTituloDocente(cbxTitulo.getSelectedItem().toString());
		
		if(professor.getId() != null) {
			aM.editarDado(professor);
			
			notifyObservers(professor);
			JOptionPane.showMessageDialog(null, "Professor salvo com sucesso!");
			
			limparFormulario();
			setVisible(false);
		} else {	
		
		aM.inserirDado(professor);
		
		JOptionPane.showMessageDialog(null, "Professor cadastrado com sucesso!");
		limparFormulario();
		}
	}
	
	public boolean validarCamposObrigatorios() {
		
		if(txfProf.getText().isEmpty() || cbxTitulo.getSelectedIndex() == 0) {
			return true;
		}
		
		return false;
	}
	
	public void limparFormulario() {
		txfProf.setText("");
		cbxTitulo.setSelectedIndex(0);
		professor = new Professor();
	}
	
	private void setarValores(Professor professor) {
		txfProf.setText(professor.getNome());
		cbxTitulo.setSelectedItem(professor.getTituloDocente());
	}
	
	@Override
	public void addObserver(ObserverProfessor o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(ObserverProfessor o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Professor professor) {
		Iterator<ObserverProfessor> it = observers.iterator();
		while (it.hasNext()) {
			ObserverProfessor observer = (ObserverProfessor) it.next();
			observer.update(professor);
		}
	}
	
}

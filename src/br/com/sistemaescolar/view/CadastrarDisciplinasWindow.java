package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Disciplina;
import br.com.sistemaescolar.observer.ObserverDisciplina;
import br.com.sistemaescolar.observer.SubjectDisciplina;

public class CadastrarDisciplinasWindow extends AbstractWindowFrame implements SubjectDisciplina{

	private static final long serialVersionUID = 4734772377961557461L;

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastrarDisciplina();
			}
		}
	};
	
	Disciplina disciplina = new Disciplina();
	
	private ArrayList<ObserverDisciplina> observers = new ArrayList<ObserverDisciplina>();
	private JLabel labes;
	private JButton btnCadastrar, btnLimpar;
	private JTextField txfDisciplina;
	private JTextField txfCodDisciplina;
	private ManipularArquivo aM = new ManipularArquivo();
	
	public CadastrarDisciplinasWindow() {
		super("Cadastrar Disciplina");
		criarComponentes();
	}
	
	public CadastrarDisciplinasWindow(Disciplina disciplina) {
		super("Editar Disciplina");
		this.disciplina = disciplina;
		criarComponentes();
		setarValores(disciplina);
	}
	
	public void criarComponentes() {
		
		labes = new JLabel("Código da disciplina:");
		labes.setBounds(15, 10, 250, 25);
		getContentPane().add(labes);
		
		txfCodDisciplina = new JTextField();
		txfCodDisciplina.setBounds(15, 30, 200, 25);
		txfCodDisciplina.setToolTipText("Informe o código da disciplina");
		getContentPane().add(txfCodDisciplina);
		txfCodDisciplina.addKeyListener(acao);
		
		labes = new JLabel("Disciplina:");
		labes.setBounds(15, 60, 250, 25);
		getContentPane().add(labes);
		
		txfDisciplina = new JTextField();
		txfDisciplina.setBounds(15, 80, 200, 25);
		txfDisciplina.setToolTipText("Informe a disciplina");
		getContentPane().add(txfDisciplina);
		txfDisciplina.addKeyListener(acao);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 130, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar o campo");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(120, 130, 95, 25);
		getContentPane().add(btnCadastrar);
		
		btnCadastrar.addKeyListener(acao);
		
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarDisciplina();
			}
		});
	
	}
	
	public void cadastrarDisciplina() {
		if(validarCamposObrigatorios()) {
			JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para cadastrar!", "",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		
		disciplina.setCodDisciplina(Integer.parseInt(txfCodDisciplina.getText()));
		disciplina.setDisciplina(txfDisciplina.getText());
		
		if(disciplina.getId() != null) {
			aM.editarDado(disciplina);
			
			notifyObservers(disciplina);
			JOptionPane.showMessageDialog(null, "Disciplina salva com sucesso!");
			
			limparFormulario();
			setVisible(false);
		} else {
		
		aM.inserirDado(disciplina);
		
		JOptionPane.showMessageDialog(null, "Disciplina cadastrada com sucesso!");
		limparFormulario();
		}
	}
	
	public boolean validarCamposObrigatorios() {
		
		if(txfDisciplina.getText().isEmpty() || txfCodDisciplina.getText().isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	public void limparFormulario() {
		txfDisciplina.setText("");
		txfCodDisciplina.setText("");
		disciplina = new Disciplina();
	}
	
	private void setarValores(Disciplina disciplina) {
		// TODO: setar valores iniciais para edição
		txfCodDisciplina.setText(Integer.toString(disciplina.getCodDisciplina()));
		txfDisciplina.setText(disciplina.getDisciplina());
	}
	
	@Override
	public void addObserver(ObserverDisciplina o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(ObserverDisciplina o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Disciplina disciplina) {
		Iterator<ObserverDisciplina> it = observers.iterator();
		while (it.hasNext()) {
			ObserverDisciplina observer = (ObserverDisciplina) it.next();
			observer.update(disciplina);
		}
	}
	
}

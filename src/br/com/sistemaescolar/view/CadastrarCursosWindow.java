package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Curso;
import br.com.sistemaescolar.observer.ObserverCursos;
import br.com.sistemaescolar.observer.SubjectCursos;

public class CadastrarCursosWindow extends AbstractWindowFrame implements SubjectCursos {

	private static final long serialVersionUID = -1040641568238182334L;
	
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastrarCurso();
			}
		}
	};
	
	private ArrayList<ObserverCursos> observers = new ArrayList<ObserverCursos>();
	private JLabel labes;
	private JButton btnSalvar, btnLimpar;
	private JTextField txfCurso;
	private ManipularArquivo aM = new ManipularArquivo();
	
	Curso curso = new Curso();
	
	public CadastrarCursosWindow() {
		super("Cadastrar Cursos");
		criarComponentes();
	}
	
	public CadastrarCursosWindow(Curso curso) {
		super("Editar Curso");
		this.curso = curso;
		criarComponentes();
		setarValores(curso);
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
		
		
		btnSalvar = new JButton(new AbstractAction("Salvar") {
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
		
		curso.setNome(txfCurso.getText());
		
		if (curso.getId() == null) {
			aM.inserirDado(curso);
			limparFormulario();
			JOptionPane.showMessageDialog(null, "Curso salvo com sucesso!");
		}
		
		if (curso.getId() != null) {
			aM.editarDado(curso);
			
			notifyObservers(curso);
			
			JOptionPane.showMessageDialog(null, "Curso editado com sucesso!");
			
			limparFormulario();
			setVisible(false);
		}
		
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
	
	public void setarValores(Curso curso) {
		txfCurso.setText(curso.getNome());
	}

	@Override
	public void addObserver(ObserverCursos o) {
		observers.add(o);
	}
	
	@Override
	public void removeObserver(ObserverCursos o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Curso cursos) {
		Iterator<ObserverCursos> it = observers.iterator();
		while (it.hasNext()) {
			ObserverCursos observer = (ObserverCursos) it.next();
			observer.update(cursos);
		}
	}}




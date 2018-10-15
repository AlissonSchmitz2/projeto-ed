
package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Curso;
import br.com.sistemaescolar.model.Fase;
import br.com.sistemaescolar.observer.ObserverFase;
import br.com.sistemaescolar.observer.SubjectFase;

public class CadastrarFasesWindow extends AbstractWindowFrame implements SubjectFase{

	private static final long serialVersionUID = -6470064732665196009L;

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastrarFase();
			}
		}
	};
	
	ManipularArquivo aM = new ManipularArquivo();
	private ArrayList<ObserverFase> observers = new ArrayList<ObserverFase>();
	private JLabel labes;
	private JButton btnSalvar, btnLimpar;
	private JComboBox<String> cbxCurso, cbxFases;
	List<Curso> curso;

	public CadastrarFasesWindow() {
		super("Cadastrar Fase");
		curso = aM.pegarCursos();
		criarComponentes();
	}
	
	public CadastrarFasesWindow(Fase fase) {
		super("Editar Fase");
		this.fase = fase;
		curso = aM.pegarCursos();
		criarComponentes();
		setarValores(fase);
	}

	Fase fase = new Fase();

	public void criarComponentes() {

		labes = new JLabel("Fase:");
		labes.setBounds(15, 60, 250, 25);
		getContentPane().add(labes);

		cbxFases = new JComboBox<String>();
		cbxFases.addItem("-Selecione-");
		for (int i = 1; i < 11; i++) {
			if (i > 9) {
				cbxFases.addItem(i + "-FASE");
			} else {
				cbxFases.addItem("0" + i + "-FASE");
			}
		}

		cbxFases.setBounds(15, 80, 200, 25);
		cbxFases.setToolTipText("Informe a fase");
		getContentPane().add(cbxFases);
		cbxFases.addKeyListener(acao);

		labes = new JLabel("Curso:");
		labes.setBounds(15, 10, 250, 25);
		getContentPane().add(labes);

		cbxCurso = new JComboBox<String>();
		cbxCurso.addItem("-Selecione-");

		// Opções de cursos
		opcoesCursos(curso).forEach(cursos -> cbxCurso.addItem(cursos));
		cbxCurso.setBounds(15, 30, 200, 25);
		cbxCurso.setToolTipText("Informe o curso");
		getContentPane().add(cbxCurso);
		cbxCurso.addKeyListener((KeyListener) acao);

		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 130, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar o campo");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(fase.getId() != null) {
					setarValores(fase);					
				} else {
					limparFormulario();
				}

			}
		});

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(120, 130, 95, 25);
		getContentPane().add(btnSalvar);
		btnSalvar.addKeyListener(acao);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarFase();
			}
		});
	}

	public void cadastrarFase() {
		if (validarCamposObrigatorios()) {
			JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para cadastrar!", "",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		
		Curso curso = new Curso();
		curso = aM.pegarCursoPorNome(cbxCurso.getSelectedItem().toString());
		
		fase.setNome(cbxFases.getSelectedItem().toString());
		fase.setCurso(curso);

		if(fase.getId() != null) {
		aM.editarDado(fase);
		
		notifyObservers(fase);
		JOptionPane.showMessageDialog(null, "Fase salva com sucesso!");
		
		limparFormulario();
		setVisible(false);
		
		}else {
		aM.inserirDado(fase);

		JOptionPane.showMessageDialog(null, "Fase cadastrada com sucesso!");
		
		limparFormulario();
		}
	}

	public boolean validarCamposObrigatorios() {

		if ("-Selecione-".equals(cbxFases.getSelectedItem()) || "-Selecione-".equals(cbxCurso.getSelectedItem())) {
			return true;
		}

		return false;
	}

	public void limparFormulario() {
		cbxFases.setSelectedItem("-Selecione-");
		cbxCurso.setSelectedItem("-Selecione-");
		
		fase = new Fase();
	}

	private List<String> opcoesCursos(List<Curso> cursos) {
		return cursos.stream().map(curso -> curso.getNome()).distinct().collect(Collectors.toList());
	}
	
	private void setarValores(Fase fase) {
		cbxCurso.setSelectedItem(fase.getCurso().getNome());
		cbxFases.setSelectedItem(fase.getNome());
	}
	
	@Override
	public void addObserver(ObserverFase o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(ObserverFase o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Fase fase) {
		Iterator<ObserverFase> it = observers.iterator();
		while (it.hasNext()) {
			ObserverFase observer = (ObserverFase) it.next();
			observer.update(fase);
		}
	}


}

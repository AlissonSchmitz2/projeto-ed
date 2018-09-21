package br.com.sistemaescolar.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportarWindow extends AbstractWindowFrame{
	
	private JLabel labelDescricao;
	private JTextField txfData;
	private JTextField txfFaseInicial;
	private JTextField txfFaseFinal;
	private JTextField txfCurso;
	private JTextField txfImportacao;
	private JButton btnImportar;
	private JList<String> listDisciplinas;
	private JList<String> listFases;
	private JList<String> listProfessores;
	private DefaultListModel<String> modelDisciplinas = new DefaultListModel<String>();
	private DefaultListModel<String> modelFases = new DefaultListModel<String>();
	private DefaultListModel<String> modelProfessores = new DefaultListModel<String>();
	
	public ImportarWindow() {
		super("Importar Arquivo");
		criarComponentes();
	}
	
	public void criarComponentes() {
		
		labelDescricao = new JLabel("Arquivo:");
		labelDescricao.setBounds(15, 10, 100, 25);
		getContentPane().add(labelDescricao);
		
		txfImportacao = new JTextField();
		txfImportacao.setEditable(false);
		txfImportacao.setBackground(Color.WHITE);
		txfImportacao.setBounds(15, 32, 350, 25);
		getContentPane().add(txfImportacao);
		
		btnImportar = new JButton(new AbstractAction("Importar") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {								
				txfImportacao.setText(fileChooser());
			}
		});
		btnImportar.setBounds(370, 32, 120, 25);
		getContentPane().add(btnImportar);		
		
		labelDescricao = new JLabel("Data:");
		labelDescricao.setBounds(15, 80, 100, 25);
		getContentPane().add(labelDescricao);
		
		txfData = new JTextField();
		txfData.setEditable(false);
		txfData.setBackground(Color.WHITE);
		txfData.setBounds(50, 80, 80, 25);
		getContentPane().add(txfData);
		
		labelDescricao = new JLabel("Fase Inicial:");
		labelDescricao.setBounds(145, 80, 100, 25);
		getContentPane().add(labelDescricao);
		
		txfFaseInicial = new JTextField();
		txfFaseInicial.setEditable(false);
		txfFaseInicial.setBackground(Color.WHITE);
		txfFaseInicial.setBounds(215, 80, 25, 25);
		getContentPane().add(txfFaseInicial);
		
		labelDescricao = new JLabel("Fase Final:");
		labelDescricao.setBounds(255, 80, 100, 25);
		getContentPane().add(labelDescricao);
		
		txfFaseFinal = new JTextField();
		txfFaseFinal.setEditable(false);
		txfFaseFinal.setBackground(Color.WHITE);
		txfFaseFinal.setBounds(318, 80, 25, 25);
		getContentPane().add(txfFaseFinal);
		
		labelDescricao = new JLabel("Curso:");
		labelDescricao.setBounds(360, 80, 100, 25);
		getContentPane().add(labelDescricao);
		
		txfCurso = new JTextField();
		txfCurso.setEditable(false);
		txfCurso.setBackground(Color.WHITE);
		txfCurso.setBounds(401, 80, 160, 25);
		getContentPane().add(txfCurso);
		
		labelDescricao = new JLabel("FASES:");
		labelDescricao.setBounds(20, 145, 100, 25);
		getContentPane().add(labelDescricao);
		
		listFases = new JList<String>(modelFases);
		JScrollPane scrollFases = new JScrollPane(listFases);
		scrollFases.setBounds(20, 165, 300, 450);
		getContentPane().add(scrollFases, BorderLayout.CENTER);
		
		labelDescricao = new JLabel("DISCIPLINAS:");
		labelDescricao.setBounds(360, 145, 100, 25);
		getContentPane().add(labelDescricao);
		
		listDisciplinas = new JList<String>(modelDisciplinas);
		JScrollPane scrollDisciplinas = new JScrollPane(listDisciplinas);
		scrollDisciplinas.setBounds(360, 165, 300, 450);
		getContentPane().add(scrollDisciplinas, BorderLayout.CENTER);
		
		labelDescricao = new JLabel("PROFESSORES:");
		labelDescricao.setBounds(700, 145, 100, 25);
		getContentPane().add(labelDescricao);
		
		listProfessores = new JList<String>(modelProfessores);
		JScrollPane scrollProfessores = new JScrollPane(listProfessores);
		scrollProfessores.setBounds(700, 165, 300, 450);
		getContentPane().add(scrollProfessores, BorderLayout.CENTER);
		
		for (int i = 0; i < 50; i++) {
			modelDisciplinas.add(i, "TESTE D");
			modelFases.add(i, "TESTE F");
			modelProfessores.add(i, "TESTE P");
		}
	}
	
	private String fileChooser() {
		
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);

	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	      return chooser.getSelectedFile().getPath().toString();
	    }

	    return "Erro na seleção do arquivo";
	}
	

	private static final long serialVersionUID = 1061952785349906171L;

}

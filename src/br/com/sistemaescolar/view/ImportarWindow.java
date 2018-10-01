package br.com.sistemaescolar.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.sistemaescolar.importation.ImportacaoDados;
import br.com.sistemaescolar.importation.ResumoDisciplina;
import br.com.sistemaescolar.importation.ResumoFase;
import br.com.sistemaescolar.importation.ResumoProfessor;

public class ImportarWindow extends AbstractWindowFrame{
	
	private JLabel labelDescricao;
	private JTextField txfData;
	private JTextField txfFaseInicial;
	private JTextField txfFaseFinal;
	private JTextField txfCurso;
	private JTextField txfImportacao;
	private JButton btnSelecionarArquivo;
	
	private JList<ResumoFase> listFases;
	private JList<ResumoDisciplina> listDisciplinas;
	private JList<ResumoProfessor> listProfessores;
	private DefaultListModel<ResumoFase> modelFases = new DefaultListModel<ResumoFase>();
	private DefaultListModel<ResumoDisciplina> modelDisciplinas = new DefaultListModel<ResumoDisciplina>();
	private DefaultListModel<ResumoProfessor> modelProfessores = new DefaultListModel<ResumoProfessor>();
	
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
		
		btnSelecionarArquivo = new JButton("Selecionar Arquivo");
		btnSelecionarArquivo.addActionListener(acaoBotaoSelecionarArquivo());
		
		btnSelecionarArquivo.setBounds(370, 32, 160, 25);
		getContentPane().add(btnSelecionarArquivo);		
		
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
		txfFaseInicial.setBounds(215, 80, 75, 25);
		getContentPane().add(txfFaseInicial);	
		
		labelDescricao = new JLabel("Fase Final:");
		labelDescricao.setBounds(305, 80, 100, 25);
		getContentPane().add(labelDescricao);
		
		txfFaseFinal = new JTextField();
		txfFaseFinal.setEditable(false);
		txfFaseFinal.setBackground(Color.WHITE);
		txfFaseFinal.setBounds(368, 80, 75, 25);
		getContentPane().add(txfFaseFinal);
		
		labelDescricao = new JLabel("Curso:");
		labelDescricao.setBounds(460, 80, 100, 25);
		getContentPane().add(labelDescricao);
		
		txfCurso = new JTextField();
		txfCurso.setEditable(false);
		txfCurso.setBackground(Color.WHITE);
		txfCurso.setBounds(501, 80, 160, 25);
		getContentPane().add(txfCurso);
		
		labelDescricao = new JLabel("FASES:");
		labelDescricao.setBounds(20, 145, 100, 25);
		getContentPane().add(labelDescricao);
		
		listFases = new JList<ResumoFase>(modelFases);
		JScrollPane scrollFases = new JScrollPane(listFases);
		scrollFases.setBounds(20, 165, 300, 450);
		getContentPane().add(scrollFases, BorderLayout.CENTER);
		listFases.addMouseListener(acaoSelecionarFase());
		
		labelDescricao = new JLabel("DISCIPLINAS:");
		labelDescricao.setBounds(360, 145, 100, 25);
		getContentPane().add(labelDescricao);
		
		listDisciplinas = new JList<ResumoDisciplina>(modelDisciplinas);
		JScrollPane scrollDisciplinas = new JScrollPane(listDisciplinas);
		scrollDisciplinas.setBounds(360, 165, 300, 450);
		getContentPane().add(scrollDisciplinas, BorderLayout.CENTER);
		listDisciplinas.addMouseListener(acaoSelecionarDisciplina());
		
		labelDescricao = new JLabel("PROFESSORES:");
		labelDescricao.setBounds(700, 145, 100, 25);
		getContentPane().add(labelDescricao);
		
		listProfessores = new JList<ResumoProfessor>(modelProfessores);
		JScrollPane scrollProfessores = new JScrollPane(listProfessores);
		scrollProfessores.setBounds(700, 165, 300, 450);
		getContentPane().add(scrollProfessores, BorderLayout.CENTER);
		listProfessores.addMouseListener(acaoSelecionarProfessor());
	}
	
	private AbstractAction acaoBotaoSelecionarArquivo() {
		return new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				String arquivoSelecionado = fileChooser();
				
				if (arquivoSelecionado != null) {
					txfImportacao.setText(arquivoSelecionado);
					
					try {
						ImportacaoDados importacaoDados = new ImportacaoDados(arquivoSelecionado);
						
						//Seta as informações nos components da tela
						DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						txfData.setText(dateFormat.format(importacaoDados.getHeader().getDataProcessamento()));
						
						txfCurso.setText(importacaoDados.getHeader().getNomeCurso());
						
						txfFaseInicial.setText(importacaoDados.getHeader().getFaseInicial());
						txfFaseFinal.setText(importacaoDados.getHeader().getFaseFinal());
						
						//Listas
						modelFases.clear();
						importacaoDados.getResumos().values().stream().forEach(resumoFase -> modelFases.addElement(resumoFase));
						
						modelDisciplinas.clear();
						importacaoDados.getTodasDisciplinas().values().stream().forEach(resumoDisciplina -> modelDisciplinas.addElement(resumoDisciplina));
						
						modelProfessores.clear();
						importacaoDados.getTodosProfessores().values().stream().forEach(resumoProfessor -> modelProfessores.addElement(resumoProfessor));
					} catch (Exception erro) {
						//TODO: Limpar todos os valores dos componentes e desabilitar botão de processamento da importação
						JOptionPane.showMessageDialog(rootPane, erro.getMessage(), "", JOptionPane.ERROR_MESSAGE, null);
					}
				} else {
					//TODO: Limpar todos os valores dos componentes e desabilitar botão de processamento da importação
					txfImportacao.setText("");
				}
			}
		};
	}
	
	private MouseListener acaoSelecionarFase() {
		return new MouseAdapter() {
			public void mouseReleased(MouseEvent mouseEvent) {
				List<Integer> disciplinasParaSelecionar = new ArrayList<Integer>();
				List<Integer> professoresParaSelecionar = new ArrayList<Integer>();
				
				for (int indiceFaseSelecionada : listFases.getSelectedIndices()) {
					//Percorre todas as disciplinas da lista para identificar aquelas que pertencem a fase selecionada
					for(int i = 0; i < listDisciplinas.getModel().getSize(); i++) {
						ResumoDisciplina disciplina = listFases.getModel().getElementAt(indiceFaseSelecionada).getDisciplinas()
								.get(listDisciplinas.getModel().getElementAt(i).getCodigoDisciplina());
							
						if (disciplina != null) {
							disciplinasParaSelecionar.add(i);
							
							//Percorre todas os professores da lista para identificar aquelas que pertencem a disciplina selecionada
							for(int j = 0; j < listProfessores.getModel().getSize(); j++) {
								ResumoProfessor professor = disciplina.getProfessores()
										.get(listProfessores.getModel().getElementAt(j).getNome());
									
								if (professor != null) {
									professoresParaSelecionar.add(j);
								}
							}
						}
					}
        		}

				listDisciplinas.setSelectedIndices(disciplinasParaSelecionar.stream().mapToInt(i -> i).toArray());
				listProfessores.setSelectedIndices(professoresParaSelecionar.stream().mapToInt(i -> i).toArray());
			}
	    };
	}
	
	private MouseListener acaoSelecionarDisciplina() {
		return new MouseAdapter() {
			public void mouseReleased(MouseEvent mouseEvent) {
				List<Integer> fasesParaSelecionar = new ArrayList<Integer>();
				List<Integer> professoresParaSelecionar = new ArrayList<Integer>();

				for (int indiceDisciplinaSelecionada : listDisciplinas.getSelectedIndices()) {
					//Percorre todas as fases da lista para identificar aquelas que a disciplina selecionada pertence
					for(int i = 0; i < listFases.getModel().getSize(); i++) {
						ResumoFase fase = listFases.getModel().getElementAt(i);
						ResumoDisciplina disciplina = fase.getDisciplinas().get(listDisciplinas.getModel().getElementAt(indiceDisciplinaSelecionada).getCodigoDisciplina());
						
						if (disciplina != null) {
							fasesParaSelecionar.add(i);
							
							//Percorre todas os professores da lista para identificar aquelas que pertencem a disciplina selecionada
							for(int j = 0; j < listProfessores.getModel().getSize(); j++) {
								ResumoProfessor professor = disciplina.getProfessores()
										.get(listProfessores.getModel().getElementAt(j).getNome());
									
								if (professor != null) {
									professoresParaSelecionar.add(j);
								}
							}
						}
					}
        		}
				
				listFases.setSelectedIndices(fasesParaSelecionar.stream().mapToInt(i -> i).toArray());
				listProfessores.setSelectedIndices(professoresParaSelecionar.stream().mapToInt(i -> i).toArray());
			}
	    };
	}
	
	private MouseListener acaoSelecionarProfessor() {
		return new MouseAdapter() {
			public void mouseReleased(MouseEvent mouseEvent) {
				List<Integer> fasesParaSelecionar = new ArrayList<Integer>();
				List<Integer> disciplinasParaSelecionar = new ArrayList<Integer>();

				for (int indiceProfessorSelecionado : listProfessores.getSelectedIndices()) {
					
					
					//Percorre todas as fases da lista para identificar aquelas que a disciplina selecionada pertence
					for(int i = 0; i < listFases.getModel().getSize(); i++) {
						ResumoFase fase = listFases.getModel().getElementAt(i);
						
						//Percorre todas as disciplinas da lista para identificar aquelas que o professor selecionado pertence
						for(int j = 0; j < listDisciplinas.getModel().getSize(); j++) {
							ResumoDisciplina disciplina = fase.getDisciplinas()
									.get(listDisciplinas.getModel().getElementAt(j).getCodigoDisciplina());
							
							if (disciplina != null) {
								ResumoProfessor professor = disciplina.getProfessores().get(listProfessores.getModel().getElementAt(indiceProfessorSelecionado).getNome());
								
								if (professor != null) {
									disciplinasParaSelecionar.add(j);
									fasesParaSelecionar.add(i);
								}
							}
						}
					}
        		}
				
				listFases.setSelectedIndices(fasesParaSelecionar.stream().mapToInt(i -> i).toArray());
				listDisciplinas.setSelectedIndices(disciplinasParaSelecionar.stream().mapToInt(i -> i).toArray());
			}
	    };
	}
	
	private String fileChooser() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);

	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	      return chooser.getSelectedFile().getPath().toString();
	    }

	    return null;
	}
	

	private static final long serialVersionUID = 1061952785349906171L;

}

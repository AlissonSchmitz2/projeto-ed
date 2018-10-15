package br.com.sistemaescolar.view;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Aluno;
import br.com.sistemaescolar.model.Matricula;

public class CadastrarMatriculaWindow extends AbstractWindowFrame{
	
	private static final long serialVersionUID = 1L;
	
	ManipularArquivo mA = new ManipularArquivo();
	
	private JTextField txfNumeroMatricula;
	private JComboBox<String> cbxAluno;
	private JFormattedTextField ftxfDataMatricula;
	private JFormattedTextField ftxfDiaVencimento;
	private JButton btnAddGrade;
	private JLabel labes;
	private List<Aluno> listaAlunos;
	
	public CadastrarMatriculaWindow() {
		super("Cadastrar Matricula");
		this.listaAlunos = mA.pegarAlunos();
		criarComponentes();
	}
	
	public void criarComponentes() {
		labes = new JLabel("Matricula:");
		labes.setBounds(410, 20, 250, 25);
		getContentPane().add(labes);
		
		txfNumeroMatricula = new JTextField();
		txfNumeroMatricula.setBounds(520,20,80,25);
		txfNumeroMatricula.setEnabled(false);
		getContentPane().add(txfNumeroMatricula);
		
		labes = new JLabel("Aluno:");
		labes.setBounds(630, 20, 250, 25);
		getContentPane().add(labes);
		
		cbxAluno = new JComboBox<String>();
		cbxAluno.setBounds(680,20,135,25);
		cbxAluno.addItem("-Selecione-");
		getContentPane().add(cbxAluno);
		opcoesAlunos(listaAlunos).forEach(alunos -> cbxAluno.addItem(alunos));
		
		labes = new JLabel("Data de Matricula:");
		labes.setBounds(410, 60, 250, 25);
		getContentPane().add(labes);
	
			
			try {
				ftxfDataMatricula = new JFormattedTextField(new MaskFormatter("##/##/####"));
				ftxfDataMatricula.setFocusLostBehavior(JFormattedTextField.COMMIT);
				ftxfDataMatricula.setBounds(520, 60, 80, 25);
				ftxfDataMatricula.setToolTipText("Digite a data de matricula");
				getContentPane().add(ftxfDataMatricula);
				
				labes = new JLabel("Dia de Vencimento:");
				labes.setBounds(630, 60, 250, 25);
				getContentPane().add(labes);
				
				ftxfDiaVencimento = new JFormattedTextField(new MaskFormatter("##"));
				ftxfDiaVencimento.setFocusLostBehavior(JFormattedTextField.COMMIT);
				ftxfDiaVencimento.setBounds(780, 60, 35, 25);
				ftxfDiaVencimento.setToolTipText("Digite o dia de vencimento");
				getContentPane().add(ftxfDiaVencimento);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		btnAddGrade = new JButton("Adicionar Grade");
		btnAddGrade.setBounds(410, 120, 150, 25);
		getContentPane().add(btnAddGrade);
		//TODO:Abrir grade para registro
		//TODO:Vincular grades a alunos e gerar txt do mesmo
		
	
	}
	
	private List<String> opcoesAlunos(List<Aluno> alunos) {
		return alunos.stream().map(aluno -> aluno.getNomeAluno()).distinct().collect(Collectors.toList());
	}

}

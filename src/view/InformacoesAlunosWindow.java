package view;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;

import lib.ManipularArquivo;
import model.Aluno;

public class InformacoesAlunosWindow extends AbstractWindowFrame{	
	
	private static final long serialVersionUID = 1L;
	
	private JTextField txfNome;
	private JTextField txfCod;
	private JTextField txfEmail;
	private JTextField txfObs;
	private JTextField txfEnder;
	private JTextField txfNum;
	private JTextField txfComplemento;
	private JTextField txfBairro;
	private JTextField txfSexo;
	private JTextField txfCidade;
	private JTextField txfUf;
	private JTextField txfPais;
	private JTextField txfData;
	private JTextField txfFone;
	private JTextField txfCel;
	private JTextField txfCep;
	private JLabel labes;
	
	private Aluno alunoSelecionado;
	private ManipularArquivo mA = new ManipularArquivo();

	public InformacoesAlunosWindow(String idSelecionado) {
		super("Informações do Aluno");
		alunoSelecionado = mA.pegarAlunoPorId(Integer.parseInt(idSelecionado));
		criarComponentes();		
	}
	
	public void criarComponentes() {
		labes = new JLabel("Código do Aluno:");
		labes.setBounds(15, 10, 250, 25);
		getContentPane().add(labes);

		txfCod = new JTextField(alunoSelecionado.getCodAluno());
		txfCod.setBackground(Color.WHITE);
		txfCod.setEditable(false);
		txfCod.setBounds(15, 30, 125, 25);
		getContentPane().add(txfCod);

		labes = new JLabel("Nome:");
		labes.setBounds(15, 60, 350, 25);
		getContentPane().add(labes);

		txfNome = new JTextField(alunoSelecionado.getNomeAluno());
		txfNome.setBackground(Color.WHITE);
		txfNome.setEditable(false);
		txfNome.setBounds(15, 80, 350, 25);
		getContentPane().add(txfNome);

		labes = new JLabel("Sexo:");
		labes.setBounds(175, 110, 250, 25);
		getContentPane().add(labes);
		
		txfSexo = new JTextField(alunoSelecionado.getSexo());
		txfSexo.setBackground(Color.WHITE);
		txfSexo.setEditable(false);
		txfSexo.setBounds(175, 130, 190, 25);
		getContentPane().add(txfSexo);
		
		labes = new JLabel("Data de Nascimento:");
		labes.setBounds(15, 110, 230, 25);
		getContentPane().add(labes);
		
		txfData = new JTextField(alunoSelecionado.getDataNascimento());
		txfData.setBackground(Color.WHITE);
		txfData.setEditable(false);
		txfData.setBounds(15, 130, 125, 25);
		getContentPane().add(txfData);
		
		labes = new JLabel("Telefone:");
		labes.setBounds(15, 160, 125, 25);
		getContentPane().add(labes);

		txfFone = new JTextField(alunoSelecionado.getTelefone());
		txfFone.setBackground(Color.WHITE);
		txfFone.setEditable(false);
		txfFone.setBounds(15, 180, 125, 25);
		getContentPane().add(txfFone);
		
		labes = new JLabel("Celular:");
		labes.setBounds(175, 160, 190, 25);
		getContentPane().add(labes);

		txfCel = new JTextField(alunoSelecionado.getCelular());
		txfCel.setBackground(Color.WHITE);
		txfCel.setEditable(false);
		txfCel.setBounds(175, 180, 190, 25);
		getContentPane().add(txfCel);
		
		labes = new JLabel("CEP:");
		labes.setBounds(450, 110, 100, 25);
		getContentPane().add(labes);

		txfCep = new JTextField(alunoSelecionado.getCep());
		txfCep.setBackground(Color.WHITE);
		txfCep.setEditable(false);
		txfCep.setBounds(450, 130, 100, 25);
		getContentPane().add(txfCep);
		
		labes = new JLabel("Email:");
		labes.setBounds(15, 210, 350, 25);
		getContentPane().add(labes);

		txfEmail = new JTextField(alunoSelecionado.getEmail());
		txfEmail.setBackground(Color.WHITE);
		txfEmail.setEditable(false);
		txfEmail.setBounds(15, 230, 350, 25);
		getContentPane().add(txfEmail);
		
		labes = new JLabel("Observação:");
		labes.setBounds(15, 260, 350, 25);
		getContentPane().add(labes);

		txfObs = new JTextField(alunoSelecionado.getObservacao());
		txfObs.setBackground(Color.WHITE);
		txfObs.setEditable(false);
		txfObs.setBounds(15, 280, 350, 25);
		getContentPane().add(txfObs);
		
		labes = new JLabel("Endereço:");
		labes.setBounds(450, 10, 250, 25);
		getContentPane().add(labes);

		txfEnder = new JTextField(alunoSelecionado.getEndereco());
		txfEnder.setBackground(Color.WHITE);
		txfEnder.setEditable(false);
		txfEnder.setBounds(450, 30, 200, 25);
		getContentPane().add(txfEnder);
		
		labes = new JLabel("Número:");
		labes.setBounds(675, 10, 50, 25);
		getContentPane().add(labes);

		txfNum = new JTextField(alunoSelecionado.getNumero());
		txfNum.setBackground(Color.WHITE);
		txfNum.setEditable(false);
		txfNum.setBounds(675, 30, 75, 25);
		getContentPane().add(txfNum);

		labes = new JLabel("Complemento:");
		labes.setBounds(450, 60, 250, 25);
		getContentPane().add(labes);

		txfComplemento = new JTextField(alunoSelecionado.getComplemento());
		txfComplemento.setBackground(Color.WHITE);
		txfComplemento.setEditable(false);
		txfComplemento.setBounds(450, 80, 300, 25);
		getContentPane().add(txfComplemento);

		labes = new JLabel("Bairro:");
		labes.setBounds(570, 110, 250, 25);
		getContentPane().add(labes);

		txfBairro = new JTextField(alunoSelecionado.getBairro());
		txfBairro.setBackground(Color.WHITE);
		txfBairro.setEditable(false);
		txfBairro.setBounds(570, 130, 180, 25);
		getContentPane().add(txfBairro);
		
		labes = new JLabel("País:");
		labes.setBounds(450, 160, 250, 25);
		getContentPane().add(labes);
		
		txfPais = new JTextField(alunoSelecionado.getPais());
		txfPais.setBackground(Color.WHITE);
		txfPais.setEditable(false);
		txfPais.setBounds(450, 180, 200, 25);
		getContentPane().add(txfPais);
		
		labes = new JLabel("UF:");
		labes.setBounds(450, 210, 250, 25);
		getContentPane().add(labes);
		
		txfUf = new JTextField(alunoSelecionado.getUf());
		txfUf.setBackground(Color.WHITE);
		txfUf.setEditable(false);
		txfUf.setBounds(450, 230, 200, 25);
		getContentPane().add(txfUf);
		
		labes = new JLabel("Cidade:");
		labes.setBounds(450, 260, 250, 25);
		getContentPane().add(labes);
		
		txfCidade = new JTextField(alunoSelecionado.getCidade());
		txfCidade.setBackground(Color.WHITE);
		txfCidade.setEditable(false);
		txfCidade.setBounds(450, 280, 200, 25);
		getContentPane().add(txfCidade);
	}
}

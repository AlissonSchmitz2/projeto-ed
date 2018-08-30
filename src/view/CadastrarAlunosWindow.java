package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import lib.ManipularArquivo;
import model.Aluno;
import model.Cidade;
import observer.ObserverAluno;
import observer.SubjectAluno;

public class CadastrarAlunosWindow extends AbstractWindowFrame implements SubjectAluno {
	private static final long serialVersionUID = -4479891238469664919L;

	private ArrayList<ObserverAluno> observers = new ArrayList<ObserverAluno>();
	private JTextField txfNome, txfCod, txfEmail, txfObs, txfEnder, txfComplemen, txfBairro;
	private JComboBox<String> cbxGenero, cbxCidade, cbxUf, cbxPais;
	private JButton btnSalvar, btnLimpar;
	private JLabel labes;
	private JFormattedTextField txfData, txfFone, txfCel, txfCep, txfNum;
	private List<Cidade> cidades;

	private Aluno aluno;
	private ManipularArquivo aM = new ManipularArquivo();

	public CadastrarAlunosWindow() {
		super("Cadastrar Aluno");
		this.aluno = new Aluno();
		cidades = aM.pegarCidades();
		criarComponentes();
	}

	public CadastrarAlunosWindow(Aluno aluno) {
		super("Editar Aluno");
		this.aluno = aluno;
		cidades = aM.pegarCidades();
		criarComponentes();
		setarValores(aluno);
	}

	public void criarComponentes() {

		// Coluna da esquerda, campos e escritas
		labes = new JLabel("Código do Aluno:");
		labes.setBounds(15, 10, 250, 25);
		getContentPane().add(labes);

		txfCod = new JTextField();
		txfCod.setBounds(15, 30, 125, 25);
		txfCod.setToolTipText("Digite o código do aluno");
		getContentPane().add(txfCod);

		labes = new JLabel("Nome:");
		labes.setBounds(15, 60, 350, 25);
		getContentPane().add(labes);

		txfNome = new JTextField();
		txfNome.setBounds(15, 80, 350, 25);
		txfNome.setToolTipText("Digite o nome completo");
		getContentPane().add(txfNome);

		labes = new JLabel("Sexo:");
		labes.setBounds(175, 110, 250, 25);
		getContentPane().add(labes);

		cbxGenero = new JComboBox<String>();
		cbxGenero.addItem("-Selecione-");
		cbxGenero.addItem("Masculino");
		cbxGenero.addItem("Feminino");
		cbxGenero.setBounds(175, 130, 190, 25);
		cbxGenero.setToolTipText("Informe o sexo");
		getContentPane().add(cbxGenero);

		labes = new JLabel("Data de Nascimento:");
		labes.setBounds(15, 110, 230, 25);
		getContentPane().add(labes);

		try {
			MaskFormatter maskDat = new MaskFormatter("##/##/####");
			MaskFormatter maskFone = new MaskFormatter("(##) #####-####");
			MaskFormatter maskCep = new MaskFormatter("#####-###");

			txfData = new JFormattedTextField(maskDat);
			txfData.setBounds(15, 130, 125, 25);
			txfData.setToolTipText("Digite a data de nascimento");
			getContentPane().add(txfData);

			labes = new JLabel("Telefone:");//
			labes.setBounds(15, 160, 125, 25);
			getContentPane().add(labes);

			txfFone = new JFormattedTextField(maskFone);
			txfFone.setBounds(15, 180, 125, 25);
			txfFone.setToolTipText("Digite o telefone");
			getContentPane().add(txfFone);

			labes = new JLabel("Celular:");//
			labes.setBounds(175, 160, 190, 25);
			getContentPane().add(labes);

			txfCel = new JFormattedTextField(maskFone);
			txfCel.setBounds(175, 180, 190, 25);
			txfCel.setToolTipText("Digite o celular");
			getContentPane().add(txfCel);

			labes = new JLabel("CEP:");
			labes.setBounds(450, 110, 100, 25);
			getContentPane().add(labes);

			txfCep = new JFormattedTextField(maskCep);
			txfCep.setBounds(450, 130, 100, 25);
			txfCep.setToolTipText("Digite o CEP");
			getContentPane().add(txfCep);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		labes = new JLabel("Email:");
		labes.setBounds(15, 210, 350, 25);
		getContentPane().add(labes);

		txfEmail = new JTextField();
		txfEmail.setBounds(15, 230, 350, 25);
		txfEmail.setToolTipText("Digite o email");
		getContentPane().add(txfEmail);

		labes = new JLabel("Observação:");
		labes.setBounds(15, 260, 350, 25);
		getContentPane().add(labes);

		txfObs = new JTextField();
		txfObs.setBounds(15, 280, 350, 25);
		txfObs.setToolTipText("Digite uma observação");
		getContentPane().add(txfObs);

		// coluna da direita, cmpos e escrita

		labes = new JLabel("Endereço:");
		labes.setBounds(450, 10, 250, 25);
		getContentPane().add(labes);

		txfEnder = new JTextField();
		txfEnder.setBounds(450, 30, 200, 25);
		txfEnder.setToolTipText("Digite o endereço");
		getContentPane().add(txfEnder);

		labes = new JLabel("Número:");
		labes.setBounds(675, 10, 50, 25);
		getContentPane().add(labes);

		txfNum = new JFormattedTextField();
		txfNum.setBounds(675, 30, 75, 25);
		txfNum.setToolTipText("Digite o número");
		getContentPane().add(txfNum);

		labes = new JLabel("Complemento:");
		labes.setBounds(450, 60, 250, 25);
		getContentPane().add(labes);

		txfComplemen = new JTextField();
		txfComplemen.setBounds(450, 80, 300, 25);
		txfComplemen.setToolTipText("Digite o complemento");
		getContentPane().add(txfComplemen);

		labes = new JLabel("Bairro:");
		labes.setBounds(570, 110, 250, 25);
		getContentPane().add(labes);

		txfBairro = new JTextField();
		txfBairro.setBounds(570, 130, 180, 25);
		txfBairro.setToolTipText("Digite o bairro");
		getContentPane().add(txfBairro);

		// País
		labes = new JLabel("País (Outra tela):");
		labes.setBounds(450, 160, 250, 25);
		getContentPane().add(labes);

		cbxPais = new JComboBox<String>();
		cbxPais.addItem("-Selecione-");
		// Opções países
		opcoesPaises(cidades).forEach(pais -> cbxPais.addItem(pais));

		cbxPais.setBounds(450, 180, 200, 25);
		cbxPais.setToolTipText("Informe o país");
		getContentPane().add(cbxPais);

		// Estado
		labes = new JLabel("UF:");
		labes.setBounds(450, 210, 250, 25);
		getContentPane().add(labes);

		cbxUf = new JComboBox<String>();
		cbxUf.addItem("-Selecione-");
		cbxUf.setBounds(450, 230, 200, 25);
		cbxUf.setToolTipText("Informe o UF");
		getContentPane().add(cbxUf);

		// Cidade
		labes = new JLabel("Cidade (Outra tela):");
		labes.setBounds(450, 260, 250, 25);
		getContentPane().add(labes);

		cbxCidade = new JComboBox<String>();
		cbxCidade.addItem("-Selecione-");
		cbxCidade.setBounds(450, 280, 200, 25);
		cbxCidade.setToolTipText("Informe a cidade");
		getContentPane().add(cbxCidade);

		// Listeners troca comboboxes
		cbxPais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String paisSelecionado = (String) cbxPais.getSelectedItem();

				// Reseta estados e cidades
				cbxUf.removeAllItems();
				cbxUf.addItem("-Selecione-");
				cbxCidade.removeAllItems();
				cbxCidade.addItem("-Selecione-");

				if (paisSelecionado == null) {
					return;
				}

				// Adiciona opções estados
				opcoesEstados(cidades, paisSelecionado).forEach(estado -> cbxUf.addItem(estado));
			}
		});

		cbxUf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String paisSelecionado = (String) cbxPais.getSelectedItem();
				String estadoSelecionado = (String) cbxUf.getSelectedItem();

				// Reseta estados e cidades
				cbxCidade.removeAllItems();
				cbxCidade.addItem("-Selecione-");

				if (paisSelecionado == null || estadoSelecionado == null) {
					return;
				}

				// Adiciona opções estados
				opcoesCidades(cidades, paisSelecionado, estadoSelecionado).forEach(cidade -> cbxCidade.addItem(cidade));
			}
		});

		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 320, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar os campos");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (aluno.getId() != null) {
					setarValores(aluno);
				} else {
					limparFormulario();
				}
			}
		});

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(120, 320, 95, 25);
		getContentPane().add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				aluno.setCodAluno(txfCod.getText());
				aluno.setNomeAluno(txfNome.getText());
				String auxSexo = cbxGenero.getSelectedItem().toString();
				aluno.setSexo(auxSexo);
				aluno.setDataNascimento(txfData.getText());
				aluno.setTelefone(txfFone.getText());
				aluno.setCelular(txfCel.getText());
				aluno.setCep(txfCep.getText());
				aluno.setEmail(txfEmail.getText());
				aluno.setObservacao(txfObs.getText());
				aluno.setEndereco(txfEnder.getText());
				aluno.setComplemento(txfComplemen.getText());
				aluno.setBairro(txfBairro.getText());
				aluno.setNumero(txfNum.getText());
				aluno.setCidade(cbxCidade.getSelectedItem().toString());
				aluno.setUf(cbxUf.getSelectedItem().toString());
				aluno.setPais(cbxPais.getSelectedItem().toString());

				boolean cadastrar = true;
				if (aluno.getId() != null) {
					notifyObservers(aluno);
					JOptionPane.showMessageDialog(null, "Aluno editado com sucesso!");
					cadastrar = false;
					setVisible(false);
				}

				if (cadastrar) {
					try {
						aM.inserirDado(aluno);
						limparFormulario();
						JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

	}

	public void limparFormulario() {

		txfNome.setText("");
		txfCod.setText("");
		txfEmail.setText("");
		txfObs.setText("");
		txfEnder.setText("");
		txfNum.setText("");
		txfComplemen.setText("");
		txfBairro.setText("");
		cbxGenero.setSelectedIndex(0);
		cbxCidade.setSelectedIndex(0);
		cbxUf.setSelectedIndex(0);
		cbxPais.setSelectedIndex(0);
		txfData.setText("");
		txfFone.setText("");
		txfCel.setText("");
		txfCep.setText("");
		
		aluno = new Aluno();
	}

	private List<String> opcoesPaises(List<Cidade> cidades) {
		return cidades.stream().map(cidade -> cidade.getPais()).distinct().collect(Collectors.toList());
	}

	private List<String> opcoesEstados(List<Cidade> cidades, String pais) {
		return cidades.stream().filter(cidade -> pais.equals(cidade.getPais())).map(cidade -> cidade.getUf()).distinct()
				.collect(Collectors.toList());
	}

	private List<String> opcoesCidades(List<Cidade> cidades, String pais, String estado) {
		return cidades.stream().filter(cidade -> pais.equals(cidade.getPais()))
				.filter(cidade -> estado.equals(cidade.getUf())).map(cidade -> cidade.getCidade()).distinct()
				.collect(Collectors.toList());
	}

	private void setarValores(Aluno aluno) {
		// TODO: setar valores iniciais para edição
		txfNome.setText(aluno.getNomeAluno());
		txfCod.setText(aluno.getCodAluno());
		txfEmail.setText(aluno.getEmail());
		txfObs.setText(aluno.getObservacao());
		txfEnder.setText(aluno.getEndereco());
		txfComplemen.setText(aluno.getComplemento());
		txfBairro.setText(aluno.getBairro());
		cbxGenero.setSelectedItem(aluno.getSexo());
		txfData.setValue(aluno.getDataNascimento());
		txfFone.setValue(aluno.getTelefone());
		txfCel.setValue(aluno.getCelular());
		txfCep.setValue(aluno.getCep());
		txfNum.setValue(aluno.getNumero());
		
		cbxPais.setSelectedItem(aluno.getPais());
		cbxUf.setSelectedItem(aluno.getUf());
		cbxCidade.setSelectedItem(aluno.getCidade());
	}

	@Override
	public void addObserver(ObserverAluno o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(ObserverAluno o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Aluno aluno) {
		Iterator it = observers.iterator();
		while (it.hasNext()) {
			ObserverAluno observer = (ObserverAluno) it.next();
			observer.update(aluno);

		}
	}

}

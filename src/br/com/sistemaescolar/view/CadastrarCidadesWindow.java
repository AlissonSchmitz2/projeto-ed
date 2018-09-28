package br.com.sistemaescolar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.sistemaescolar.lib.ManipularArquivo;
import br.com.sistemaescolar.model.Cidade;
import br.com.sistemaescolar.observer.ObserverCidade;
import br.com.sistemaescolar.observer.SubjectCidade;

public class CadastrarCidadesWindow extends AbstractWindowFrame implements SubjectCidade {
	private static final long serialVersionUID = 1L;

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastraCidade();
			}
		}
	};

	private ArrayList<ObserverCidade> observers = new ArrayList<ObserverCidade>();
	private JTextField txfCidade;
	private JComboBox<String> txfUf;
	private JTextField txfPais;
	private JLabel saida;
	private JButton btnCadastra, btnLimpar;
	
	private Cidade cidade;
	private ManipularArquivo aM = new ManipularArquivo();
	
	public CadastrarCidadesWindow() {
		super("Cadastrar Cidade");
		this.cidade = new Cidade();
		criarComponentes();
	}

	public CadastrarCidadesWindow(Cidade cidade) {
		super("Editar Cidade");
		this.cidade = cidade;
		criarComponentes();
		setarValores(cidade);
	}

	private void criarComponentes() {

		saida = new JLabel("País:");
		saida.setBounds(15, 10, 200, 25);
		getContentPane().add(saida);

		txfPais = new JTextField();
		txfPais.setBounds(15, 30, 200, 25);
		txfPais.setToolTipText("Digite o país");
		getContentPane().add(txfPais);
		txfPais.addKeyListener(acao);

		saida = new JLabel("UF");
		saida.setBounds(15, 60, 200, 25);
		getContentPane().add(saida);

		txfUf = new JComboBox<String>();
		txfUf.addItem("-Selecione-");
		txfUf.addItem("AC");
		txfUf.addItem("AL");
		txfUf.addItem("AM");
		txfUf.addItem("AP");
		txfUf.addItem("BA");
		txfUf.addItem("CE");
		txfUf.addItem("DF");
		txfUf.addItem("ES");
		txfUf.addItem("GO");
		txfUf.addItem("MA");
		txfUf.addItem("MG");
		txfUf.addItem("MS");
		txfUf.addItem("MT");
		txfUf.addItem("PA");
		txfUf.addItem("PB");
		txfUf.addItem("PE");
		txfUf.addItem("PI");
		txfUf.addItem("PR");
		txfUf.addItem("RJ");
		txfUf.addItem("RN");
		txfUf.addItem("RO");
		txfUf.addItem("RR");
		txfUf.addItem("RS");
		txfUf.addItem("SC");
		txfUf.addItem("SE");
		txfUf.addItem("SP");
		txfUf.addItem("TO");
		txfUf.setBounds(15, 80, 200, 25);
		txfUf.setToolTipText("Informe o UF");
		getContentPane().add(txfUf);
		txfUf.addKeyListener(acao);

		saida = new JLabel("Cidade:");
		saida.setBounds(15, 10, 200, 25);
		saida.setBounds(15, 110, 200, 25);
		getContentPane().add(saida);

		txfCidade = new JTextField();
		txfCidade.setBounds(15, 130, 200, 25);
		txfCidade.setToolTipText("Digite a cidade");
		getContentPane().add(txfCidade);
		txfCidade.addKeyListener(acao);

		btnLimpar = new JButton(cidade.getId() == null ? "Limpar":"Desfazer");
		btnLimpar.setBounds(15, 170, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar os campos");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cidade.getId() != null) {
					setarValores(cidade);
				} else {
					limparFormulario();
				}
			}
		});

		btnCadastra = new JButton(new AbstractAction("Salvar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				cadastraCidade();
			}
		});

		btnCadastra.addKeyListener(acao);

		btnCadastra.setBounds(120, 170, 95, 25);
		getContentPane().add(btnCadastra);
	}

	public void cadastraCidade() {

		if (validarCamposObrigatorios()) {
			JOptionPane.showMessageDialog(rootPane, "Campos obrigatórios (*) não preenchidos!", "",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}

		cidade.setCidade(txfCidade.getText());
		cidade.setUf(txfUf.getSelectedItem().toString());
		cidade.setPais(txfPais.getText());

		if (cidade.getId() == null) {
			aM.inserirDado(cidade);
			limparFormulario();
			JOptionPane.showMessageDialog(null, "Cidade salva com sucesso!");
		}

		if (cidade.getId() != null) {
			aM.editarDado(cidade);
			
			notifyObservers(cidade);
			JOptionPane.showMessageDialog(null, "Cidade salva com sucesso!");
			
			limparFormulario();
			setVisible(false);
		}
	}

	public boolean validarCamposObrigatorios() {
		if ("-Selecione-".equals(txfUf.getSelectedItem()) || txfCidade.getText().isEmpty()
				|| txfPais.getText().isEmpty()) {
			return true;
		}

		return false;
	}

	private void limparFormulario() {
		txfCidade.setText("");
		txfPais.setText("");
		txfUf.setSelectedIndex(0);

		cidade = new Cidade();
	}

	private void setarValores(Cidade cidade) {
		txfPais.setText(cidade.getPais());
		txfUf.setSelectedItem(cidade.getUf());
		txfCidade.setText(cidade.getCidade());
	}

	@Override
	public void addObserver(ObserverCidade o) {
		observers.add(o);

	}

	@Override
	public void removeObserver(ObserverCidade o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Cidade cidade) {
		Iterator it = observers.iterator();
		while (it.hasNext()) {
			ObserverCidade observer = (ObserverCidade) it.next();
			observer.update(cidade);
		}

	}
}

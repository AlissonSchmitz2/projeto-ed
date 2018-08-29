package view;

import model.Aluno;
import model.Cidade;
import observer.Observer;
import observer.ObserverCidade;
import observer.SubjectCidade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import lib.ManipularArquivo;

public class CadastrarCidadeWindow extends AbstractWindowFrame implements SubjectCidade{
	private static final long serialVersionUID = 1L;

	private ArrayList<ObserverCidade> observers = new ArrayList<ObserverCidade>();
	private JTextField txfCidade;
	private JComboBox<String> txfUf;
	private JTextField txfPais;
	private JLabel saida;
	private JButton btnCadastra, btnLimpar;	
	private Cidade cidade;

	public CadastrarCidadeWindow() {
		super("Cadastrar Cidade");
		this.cidade = new Cidade();
		criarComponentes();
	}
	
	public CadastrarCidadeWindow(Cidade cidade){
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

		saida = new JLabel("Cidade:");
		saida.setBounds(15, 10, 200, 25);
		saida.setBounds(15, 110, 200, 25);
		getContentPane().add(saida);

		txfCidade = new JTextField();
		txfCidade.setBounds(15, 130, 200, 25);
		txfCidade.setToolTipText("Digite a cidade");
		getContentPane().add(txfCidade);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 170, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar os campos");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//limparFormulario();
			}
		});
		
				
		btnCadastra = new JButton(new AbstractAction("Cadastrar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				
			
				cidade.setCidade(txfCidade.getText());
				cidade.setUf(txfUf.getSelectedItem().toString());
				cidade.setPais(txfPais.getText());
				
				boolean cadastrar = true;
				
				if(cidade.getId() != null) {
					notifyObservers(cidade);
					JOptionPane.showMessageDialog(null, "Cidade editada com sucesso!");
					cadastrar = false;
					setVisible(false);
				}
				
				if(cadastrar) {
				try {
					ManipularArquivo aM = new ManipularArquivo();
					aM.inserirDado(cidade);
					// TODO: Limpar o formulário
					JOptionPane.showMessageDialog(null,"Cidade salva com sucesso!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}}
		});
		
		btnCadastra.setBounds(120, 170, 95, 25);
		getContentPane().add(btnCadastra);
	}
	
	private void setarValores(Cidade cidade) {
		//TODO: setar valores iniciais para edição
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
		while(it.hasNext()) {
			ObserverCidade observer = (ObserverCidade) it.next();
			observer.update(cidade);
		}
		
	}
}

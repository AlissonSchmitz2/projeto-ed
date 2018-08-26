package view;

import model.Cidade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import lib.ManipularArquivo;

public class CadastrarCidadeWindow extends WindowFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txfCidade;
	private JComboBox<String> txfUf;
	private JTextField txfPais;
	private JLabel saida;
	private JButton btnCadastra, btnLimpar;

	public CadastrarCidadeWindow() {
		super("Cadastrar Cidade");

		criarComponentes();
	}

	private void criarComponentes() {

		saida = new JLabel("Pa�s:");
		saida.setBounds(15, 10, 200, 25);
		getContentPane().add(saida);

		txfPais = new JTextField();
		txfPais.setBounds(15, 30, 200, 25);
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
		getContentPane().add(txfUf);

		saida = new JLabel("Cidade:");
		saida.setBounds(15, 10, 200, 25);
		saida.setBounds(15, 110, 200, 25);
		getContentPane().add(saida);

		txfCidade = new JTextField();
		txfCidade.setBounds(15, 130, 200, 25);
		getContentPane().add(txfCidade);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(15, 170, 95, 25);
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
				Cidade cidade = new Cidade();

				String _cidade = txfCidade.getText();
				cidade.setCidade(_cidade);

				String uf = txfUf.getSelectedItem().toString();
				cidade.setUf(uf);

				String pais = txfPais.getText();
				cidade.setPais(pais);

				try {
					ManipularArquivo aM = new ManipularArquivo();
					aM.inserirDado(cidade);
					// TODO: Limpar o formul�rio
					JOptionPane.showMessageDialog(null,"Cidade salva com sucesso!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnCadastra.setBounds(120, 170, 95, 25);
		getContentPane().add(btnCadastra);
	}
}

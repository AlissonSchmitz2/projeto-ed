package view;

import model.Cidade;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import lib.ArquivoManipular;

public class CadastrarCidadeWindow extends WindowFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txfCidade;
	private JComboBox<String> txfUf;
	private JTextField txfPais;
	private JLabel saida;
	private JButton btnCadastra;

	public CadastrarCidadeWindow() {
		super("Cadastrar Cidade");

		criarComponentes();
	}

	private void criarComponentes() {
		saida = new JLabel("Cidade:");
		saida.setBounds(10, 10, 200, 25);
		getContentPane().add(saida);

		txfCidade = new JTextField();
		txfCidade.setBounds(10, 30, 200, 25);
		getContentPane().add(txfCidade);

		saida = new JLabel("UF");
		saida.setBounds(10, 60, 200, 25);
		getContentPane().add(saida);

		txfUf = new JComboBox<String>();
		txfUf.addItem("Selecione");
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
		txfUf.setBounds(10, 80, 200, 25);
		getContentPane().add(txfUf);

		saida = new JLabel("País:");
		saida.setBounds(10, 110, 200, 25);
		getContentPane().add(saida);

		txfPais = new JTextField();
		txfPais.setBounds(10, 130, 200, 25);
		getContentPane().add(txfPais);

		btnCadastra = new JButton(new AbstractAction("Cadastrar cidade") {
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
					ArquivoManipular aM = new ArquivoManipular();
					aM.criarArquivo(cidade);
					//TODO: Limpar o formulário
					//TODO: exibir mensagem de sucesso
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCadastra.setBounds(10, 160, 100, 25);
		getContentPane().add(btnCadastra);
	}
}

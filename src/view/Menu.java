package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Menu extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> cbxCadastro;
	private JButton btnVoltar;
	private JButton btnAvancar;

	Menu() {
		setSize(250, 200);
		setTitle("Menu");
		setLayout(null);
		setResizable(false);
		componenteCriar();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void componenteCriar() {
		JLabel menuLabel = new JLabel("Menu de opções");
		menuLabel.setBounds(60, 10, 200, 25);
		getContentPane().add(menuLabel);

		JLabel saida = new JLabel("Escolha uma das opções abaixo:");
		saida.setBounds(10, 50, 200, 25);
		getContentPane().add(saida);

		cbxCadastro = new JComboBox<String>();
		cbxCadastro.addItem("Selecione");
		cbxCadastro.addItem("Cadastrar Usuario");
		cbxCadastro.addItem("Cadastrar Cidade");
		cbxCadastro.addItem("Entrar na Sala");
		cbxCadastro.setBounds(10, 80, 210, 25);
		getContentPane().add(cbxCadastro);

		btnVoltar = new JButton(new AbstractAction("Voltar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new LoginWindow().setVisible(true);
			}
		});
		btnVoltar.setBounds(10, 140, 100, 25);
		getContentPane().add(btnVoltar);

		btnAvancar = new JButton(new AbstractAction("Avançar") {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				String itemSelecionado = cbxCadastro.getSelectedItem().toString();
				if (itemSelecionado.equals("Cadastrar Cidade")) {
					setVisible(false);
					new CadastraCidade().setVisible(true);
				} else if (itemSelecionado.equals("Cadastrar Usuario")) {
					setVisible(false);
					new CadastraUsuario().setVisible(true);
				} else if (itemSelecionado.equals("Entrar na Sala")) {
					setVisible(false);
					new Sala().setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Por gentileza, escolha uma das opções acima!");
				}
			}
		});
		btnAvancar.setBounds(120, 140, 100, 25);
		getContentPane().add(btnAvancar);
	}
}

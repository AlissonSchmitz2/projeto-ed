package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class SalaConvidado extends JDialog {
	private static final long serialVersionUID = 1L;
	private JLabel salaConvidado;
	private JButton btnVoltar;

	SalaConvidado() {
		setSize(800, 700);
		setTitle("Sala");
		setLayout(null);
		setResizable(false);
		ComponentesCriar();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void ComponentesCriar() {
		salaConvidado = new JLabel("SALA CONVIDADOS");
		salaConvidado.setBounds(10, 10, 200, 25);
		getContentPane().add(salaConvidado);

		btnVoltar = new JButton(new AbstractAction("Voltar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new LoginWindow().setVisible(true);
			}
		});
		btnVoltar.setBounds(10, 140, 100, 25);
		getContentPane().add(btnVoltar);
	}
}

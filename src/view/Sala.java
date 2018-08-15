package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class Sala extends JDialog {
	private static final long serialVersionUID = 1L;
	private JLabel sala;
	private JButton btnVoltar;

	Sala() {
		setSize(800, 700);
		setTitle("Sala");
		setLayout(null);
		setResizable(false);
		componentesCriar();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void componentesCriar() {
		sala = new JLabel("SALA ADMINISTRADOR");
		sala.setBounds(10, 10, 200, 25);
		getContentPane().add(sala);

		btnVoltar = new JButton(new AbstractAction("Voltar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new Menu().setVisible(true);
			}
		});
		btnVoltar.setBounds(10, 140, 100, 25);
		getContentPane().add(btnVoltar);
	}
}

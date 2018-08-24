package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListarUsuariosWindow extends WindowFrame {
	private static final long serialVersionUID = 6347451344224720236L;
	
	public ListarUsuariosWindow() {
		super("Lista de Usuários");
	}
	
	public void carregarGrid() {
		JPanel panel = new JPanel();
		
		GridBagLayout glayout = new GridBagLayout();
		panel.setLayout(glayout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		for (int i = 0; i < 60; i++) {
			JLabel button = new JLabel("Teste " + i);
			button.setBounds(0, 0, 100, 100);
			button.setMinimumSize(new Dimension(200,25));
			c.gridx = 0;
			c.gridy = i;
			panel.add(button, c);
			
			JLabel buttona = new JLabel("Teste A " + i);
			buttona.setBounds(0, 0, 100, 100);
			c.gridx = 1;
			c.gridy = i;
			panel.add(buttona, c);
			
			JButton buttonb = new JButton("Teste B " + i);
			buttonb.setBounds(0, 0, 100, 100);
			c.gridx = 2;
			c.gridy = i;
			panel.add(buttonb, c);
		}
	    
		JScrollPane scroll = new JScrollPane(panel);
		
		setLayout(new BorderLayout());
		scroll.setVisible(true);
		add(scroll);
	}
}
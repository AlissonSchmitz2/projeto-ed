package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListarUsuariosWindow extends AbstractWindowFrame {
	private static final long serialVersionUID = 6347451344224720236L;
	
	private static final int HEADER_HEIGHT = 22; //Diferença do topo a ser considerada
	
	private JScrollPane grid = null;
	private JPanel panel = null;
	private GridBagLayout glayout = null;
	private GridBagConstraints gbc = null;
	
	public ListarUsuariosWindow() {
		super("Lista de Usuários");
		
		carregarGrid();
	}

	private void carregarGrid() {
		if (panel == null) {
			panel = new JPanel();
			
			glayout = new GridBagLayout();
			panel.setLayout(glayout);
			
			gbc = new GridBagConstraints();
		}
		
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		
		//TODO: Popular a lista de usuários
		for (int i = 1; i < 60; i++) {
			JLabel button = new JLabel("Teste " + i);
			button.setMinimumSize(new Dimension(200,25));
			gbc.gridx = 0;
			gbc.gridy = i;
			panel.add(button, gbc);
			
			JLabel buttona = new JLabel("Teste A " + i);
			gbc.gridx = 1;
			gbc.gridy = i;
			panel.add(buttona, gbc);
			
			JButton buttonb = new JButton("Editar");
			gbc.gridx = 2;
			gbc.gridy = i;
			gbc.weightx = 0.02;
			buttonb.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					grid.setVisible(false);
					carregarGrid();
				}
			});
			panel.add(buttonb, gbc);
			
			JButton buttonc = new JButton("Excluir");
			gbc.gridx = 3;
			gbc.gridy = i;
			gbc.weightx = 0.02;
			buttonc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					grid.setVisible(false);
					carregarGrid();
				}
			});
			panel.add(buttonc, gbc);
		}
		//Remover até aqui
	    
		if (grid == null) {
			grid = new JScrollPane(panel);
		}
		
		//setLayout(new FlowLayout()); //Lista com preenchimento total da tela
		setLayout(null);
		redimensionarGrid(grid);
		grid.setVisible(true);
		add(grid);
	}
	
	public void redimensionarGrid(JScrollPane grid) {
		int espacoFiltroGrid = 100;
		
		//TODO: atualmente a grid ocupa é baseada no tamanho total da tela e não da aplicação
		grid.setBounds(0, espacoFiltroGrid, getWidth(), getHeight() - espacoFiltroGrid - HEADER_HEIGHT);
	}
	
	public JScrollPane getGrid() {
		return grid;
	}
}
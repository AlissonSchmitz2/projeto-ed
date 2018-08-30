package br.com.sistemaescolar.view;

import javax.swing.JScrollPane;

public abstract class AbstractGridWindow extends AbstractWindowFrame  {
	private static final long serialVersionUID = -8203026366064920547L;
	
	protected static final int HEADER_HEIGHT = 22; // Diferença do topo a ser considerada
	protected static final int ALTURA_AREA_FILTRO = 80; // Altura da área reservada para o filtro
	
	protected JScrollPane grid = null;
	
	public AbstractGridWindow(String nomeTela) {
		super(nomeTela);
	}
	
	public void redimensionarGrid(JScrollPane grid) {
		grid.setBounds(0, ALTURA_AREA_FILTRO, getWidth(), getHeight() - ALTURA_AREA_FILTRO - HEADER_HEIGHT);
	}
	
	public JScrollPane getGridContent() {
		return grid;
	}
}

package view;

import java.awt.Rectangle;

import javax.swing.JInternalFrame;

public class WindowFrame extends JInternalFrame {
	private static final long serialVersionUID = -9124809980962961247L;
	static final int xOffset = 0, yOffset = 0;

	public WindowFrame(String nomeTela) {
	    super(nomeTela, false, true, false, false);

	    this.setBounds(new Rectangle(0, 0, 796, 713));
	    
	    setLocation(xOffset, yOffset);
	    setLayout(null);
	}
}

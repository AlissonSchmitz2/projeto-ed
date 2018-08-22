package view;

import java.awt.Rectangle;

import javax.swing.JInternalFrame;

public class WindowFrame extends JInternalFrame {
	private static final long serialVersionUID = -9124809980962961247L;
	static int openFrameCount = 0;
	static final int xOffset = 30, yOffset = 30;

	public WindowFrame(String nomeTela) {
	    super(nomeTela, false, true, false, false);

	    this.setBounds(new Rectangle(0, 0, 796, 713));
	    
	    setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
	    setLayout(null);
	}
}

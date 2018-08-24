package view;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;

public class WindowFrame extends JInternalFrame {
	private static final long serialVersionUID = -9124809980962961247L;

	public WindowFrame(String nomeTela) {
	    super(nomeTela, false, true, false, false);

	    setLayout(null);
	    setVisible(true);
	    setBorder(null);
	    
	    //Seta tamanho
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds(new Rectangle(0, 0, screenSize.width, screenSize.height));
	}
}

package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class StatusBarPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	
	public StatusBarPanel() {
		setBackground(Color.black);
		setPreferredSize( new Dimension(0, 50)); // 0 so the window is not resized in abscissas
	}

}

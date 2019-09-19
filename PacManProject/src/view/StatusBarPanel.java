package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class StatusBarPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	
	public StatusBarPanel(Dimension dim) {
		setBackground(Color.black);
		setPreferredSize(dim);
	}

}

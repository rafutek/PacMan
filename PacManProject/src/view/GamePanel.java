package view;


import javax.swing.*;
import java.awt.*;



public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int PWIDTH = 450;   
	private static final int PHEIGHT = 500; 


	public GamePanel()
	{

		//setBackground(Color.white);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

		// create game components
//		obs = new Obstacles(wcTop);
//		fred = new Worm(PWIDTH, PHEIGHT, obs);



//		// set up message font
//		font = new Font("SansSerif", Font.BOLD, 24);
//		metrics = this.getFontMetrics(font);

	}


	public static int getPwidth() {
		return PWIDTH;
	}


	public static int getPheight() {
		return PHEIGHT;
	}

}
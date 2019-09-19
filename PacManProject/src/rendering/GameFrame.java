package rendering;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameFrame extends JFrame implements WindowListener
{

	private static final long serialVersionUID = 1L;

	private static int DEFAULT_FPS = 80;

	private GamePanel gamePanel;        // where the game is drawn


	public GameFrame(int period)
	{ 
		super("Game");
		makeGUI(period);

		addWindowListener( this );
		pack();
		setResizable(false);
		setVisible(true);
	}  


private void makeGUI(int period)
{
	Container c = getContentPane();    // default BorderLayout used

	gamePanel = new GamePanel(period);
	c.add(gamePanel, "Center");

//	JPanel ctrls = new JPanel();   // a row of textfields
//	ctrls.setLayout( new BoxLayout(ctrls, BoxLayout.X_AXIS));
//
//	jtfBox = new JTextField("Boxes used: 0");
//	jtfBox.setEditable(false);
//	ctrls.add(jtfBox);
//
//	jtfTime = new JTextField("Time Spent: 0 secs");
//	jtfTime.setEditable(false);
//	ctrls.add(jtfTime);
//
//	c.add(ctrls, "South");
}  // end of makeGUI()



// ----------------- window listener methods -------------

public void windowActivated(WindowEvent e) 
{ gamePanel.resumeGame();  }

public void windowDeactivated(WindowEvent e) 
{  gamePanel.pauseGame();  }


public void windowDeiconified(WindowEvent e) 
{  gamePanel.resumeGame();  }

public void windowIconified(WindowEvent e) 
{  gamePanel.pauseGame(); }


public void windowClosing(WindowEvent e)
{  gamePanel.stopGame();  }

public void windowClosed(WindowEvent e) {}
public void windowOpened(WindowEvent e) {}

// ----------------------------------------------------

public static void main(String args[])
{ 
	int fps = DEFAULT_FPS;
	if (args.length != 0)
		fps = Integer.parseInt(args[0]);

	int period = (int) 1000.0/fps;
	System.out.println("fps: " + fps + "; period: " + period + " ms");

	new GameFrame(period);    // ms
}

} 
